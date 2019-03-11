package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.entity.ShareToken;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.common.utils.DateUtils;
import com.dongpeng.common.utils.DictUtils;
import com.dongpeng.common.utils.GeoUtils;
import com.dongpeng.common.utils.J2CacheUtils;
import com.dongpeng.common.utils.ShareTokenUtils;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.entity.system.Coupon;
import com.dongpeng.entity.system.Dictionary;
import com.dongpeng.entity.system.Menu;
import com.dongpeng.entity.system.Organization;
import com.dongpeng.entity.system.Region;
import com.dongpeng.entity.system.User;
import com.dongpeng.entity.system.UserOrg;
import com.dongpeng.system.dao.OrganizationDao;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spatial4j.core.shape.Rectangle;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class OrganizationService extends BaseCrudService<OrganizationDao,Organization> {
    /** 组织编码基础 **/
    private static final String ORG_BASE_CODE="00";

    @Autowired
    private RedissonClient redisson;
    @Autowired
    private UserService userService;
    @Autowired
    private CouponService couponService;
    @Autowired
    private RegionService regionService;

    @Override
    public String createDataScopeSql(Organization entity) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public int save(Organization organization) throws OptimisticLockException {
        //如果父id不为空，则获取其名称、parentids
        String parentCode = null;
        if(null!=organization.getParentId() && 0!=organization.getParentId()){
            Organization parentOrg=dao.get(organization.getParentId());
            organization.setParentName(parentOrg.getOrgName());
            organization.setParentIds(
                    StringUtils.isNotBlank(parentOrg.getParentIds())?
                            parentOrg.getParentIds()+","+parentOrg.getId()
                            :parentOrg.getId().toString());
            parentCode=parentOrg.getOrgCode();
        }

        if(organization.getIsNewRecord()){
            organization.setOrgCode(generateOrgCode(parentCode));
        }

        //设置审核负责人
        if(null!=organization.getApprovalId() && 0!=organization.getApprovalId() && StringUtils.isBlank(organization.getApprovalName())){
            User user=userService.get(organization.getApprovalId());
            if(null!=user){
                organization.setApprovalName(user.getUserName());
            }
        }

        //设置组织类型
        if(null!=organization.getTypeId() && 0!=organization.getTypeId() && StringUtils.isBlank(organization.getTypeName())){
            Dictionary dictionary=DictUtils.getById(organization.getTypeId());
            if(null!=dictionary){
                organization.setTypeName(dictionary.getDetailName());
            }
        }

        //匹配省市区
        if(null!=organization.getProvinceId() && null!=regionService.get(organization.getProvinceId())){
            organization.setProvince(regionService.get(organization.getProvinceId()).getName());
        }
        if(null!=organization.getCityId() && null!=regionService.get(organization.getCityId())){
            organization.setCity(regionService.get(organization.getCityId()).getName());
        }
        if(null!=organization.getDistrictId() && null!=regionService.get(organization.getDistrictId())){
            organization.setDistrict(regionService.get(organization.getDistrictId()).getName());
        }

        return super.save(organization);
    }

    /**
     * 生成组织编码
     * @param parentOrgCode 父级组织编码，没有父级时传null
     * @return
     */
    private String generateOrgCode(String parentOrgCode){
        parentOrgCode=parentOrgCode==null?"":parentOrgCode;
        RAtomicLong code = redisson.getAtomicLong("org"+parentOrgCode);
        return String.valueOf(parentOrgCode+ORG_BASE_CODE+code.incrementAndGet());
    }

    /**
     * 根据name获得组织
     * @param name 组织名称
     * @return
     */
    public Organization getByName(String name){
        return dao.getByName(name);
    };

    /**
     * 根据code获得组织
     * @param code 组织名称
     * @return
     */
    public Organization getByCode(String code){
        return dao.getByCode(code);
    };

    /**
     * 根据用户id查找组织信息
     * @param userId 用户id
     * @return
     */
    public List<Organization> findListByUserId(Long userId){
        if(Global.ADMINISTRATOR_ID==userId){
            //超级管理员加载全部组织
            return dao.findAllList(new Organization());
        }else{
            return dao.findListByUserId(new UserOrg(userId));
        }
    }

    /**
     * 插入用户组织
     * <p>每个子项的userId必须一样,即只可以为一个用户批量插入组织</p>
     * @param userOrgs 用户组织列表
     * @return
     */
    @Transactional(readOnly = false)
    public int insertUserOrg(List<UserOrg> userOrgs){
        //如果新组织数据id有值，则删除该用户所有旧组织
        if(0<userOrgs.size()){
            User user=userService.get(userOrgs.get(0).getUserId());
            if(null!=user){
                deleteUserMenu(new UserOrg(userOrgs.get(0).getUserId()));
                for (UserOrg userOrg:userOrgs) {
                    //设置用户组织属性
                    userOrg.setCurrentUser();
                    userOrg.preInsert();
                    userOrg.setUserName(user.getUserName());
                }
            }

            int rs=dao.insertUserOrg(userOrgs);
            return rs;
        }else {
            return 0;
        }
    }

    /**
     * 删除用户组织
     * @param userOrg 封装用户id 和其他删除条件参数
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteUserMenu(UserOrg userOrg){
        int rs=dao.deleteUseOrg(userOrg);
        return rs;
    }

    /**
     * 按地理位置查询数据
     * @param organization
     * @param minRadius 最小查找范围 单位：千米
     * @param maxRadius 最大查找范围 单位：千米
     * @return
     */
    public List<Organization> findListByGeo(Organization organization,int minRadius,int maxRadius) {
        if(organization.isDataScopeEnbale()){
            dataScopeFilter(organization);
        }
        //先查找出经纬度范围
        Rectangle rectangle;
        if(0==minRadius){
            rectangle=GeoUtils.getPointRangeByRadius(organization.getLng().doubleValue(), organization.getLat().doubleValue(), maxRadius);
        }else {
            rectangle=GeoUtils.getPointRangeByRingRadius(organization.getLng().doubleValue(), organization.getLat().doubleValue(), minRadius,maxRadius);
        }


        organization.setBeginLng(BigDecimal.valueOf(rectangle.getMinX()));
        organization.setEndLng(BigDecimal.valueOf(rectangle.getMaxX()));

        organization.setBeginLat(BigDecimal.valueOf(rectangle.getMinY()));
        organization.setEndLat(BigDecimal.valueOf(rectangle.getMaxY()));

        //只查找门店
        Dictionary dictionary=DictUtils.getByCode(Global.DICT_ORG_TYPE_MD);
        organization.setTypeId(dictionary.getId());

        List<Organization> organizationList=dao.findList(organization);

        //计算出门店和用户的距离
        for (Organization org:organizationList) {
            org.setDistance(GeoUtils.getDistanceByPoint(
                    org.getLng().doubleValue(), org.getLat().doubleValue()
                    , organization.getLng().doubleValue(), organization.getLat().doubleValue()));
        }

        //使用Collections的sort方法，并且重写compare方法,按距离排序
        Collections.sort(organizationList, (org1, org2) -> {
            double distance1= org1.getDistance();
            double distance2= org2.getDistance();
            if (org1.getDistance() > org2.getDistance()) {
                return 1;
            } else if (distance1 == distance2) {
                return 0;
            } else {
                return -1;
            }
        });

        return organizationList;
    }

    /**
     * 获取范围内最近的组织
     * @param organization 封装用户坐标，组织id
     * @param radius 范围
     * @return
     */
    public Organization getNearestDistance(Organization organization,int radius){
        organization.setParentIds(organization.getId().toString());//该组织下的子组织也在查找范围
        List<Organization> organizationList=findListByGeo(organization, 0,radius);
        if(StringPlusUtils.isBlankList(organizationList)){
            return null;
        }else{
            return organizationList.get(0);
        }
    }

    /**
     * 按地理位置查询数据,并查询出对应优惠券
     * @param organization
     * @param minRadius 最小查找范围 单位：千米
     * @param maxRadius 最大查找范围 单位：千米
     * @return
     */
    public List<Organization> findListByGeoAndCoupons(Organization organization,int minRadius,int maxRadius) {
        List<Organization> organizationList=findListByGeo(organization, minRadius,maxRadius);
        List<Long> couponIds= Lists.newArrayList();
        if(!StringPlusUtils.isBlankList(organizationList)){
            for (Organization org:organizationList) {
                ResponseResult rs=couponService.findCouponsByOrgId(org.getId());
                //logger.info(rs.toString());

                Map<String,Object> couponInfo= (Map<String, Object>) rs.getBody().get("data");
                //logger.info(couponInfo.toString());
                org.setCoupons((List<Coupon>) couponInfo.get("data"));

                org.setReceiveNum((Integer) couponInfo.get("receiveNum"));

            /*//优惠券去重
            Iterator<Coupon> it = org.getCoupons().iterator();
            while(it.hasNext()){
                Coupon coupon = it.next();
                if(couponIds.contains(coupon.getId())){
                    it.remove();//移除该优惠券
                }else{
                    couponIds.add(coupon.getId());
                }
            }*/
            }
        }
        return organizationList;
    }

    /**
     * 从excel导入插入数据
     * @param organization
     * @return
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public int saveForExcel(Organization organization) throws Exception {
        //处理非法输入的phone
        organization.setPhone(StringPlusUtils.clearInvisibleUnicode(organization.getPhone()));

        //匹配组织，决定是insert还是update
        if(StringUtils.isBlank(organization.getOrgName())){
            return 0;
        }else {
            //从数据库获取匹配
            Organization orgFromDatabase =dao.getByNameAndParentName(organization.getOrgName(), organization.getParentName());
            if(null!=orgFromDatabase){
                //将orgFromDatabase值 赋给 organization 中为空的值
                organization.setId(orgFromDatabase.getId());
                organization.setIsNewRecord(false);
                //BeanUtils.copyBeanNotNull2Bean(orgFromDatabase, organization);
            }
        }

        //匹配审批负责人信息
        if(StringPlusUtils.isBlank(organization.getApprovalName())){
            return 0;
        }else{
            User user=userService.getByUserName(organization.getApprovalName());
            if(null==user){
                return 0;
            }else{
                organization.setApprovalId(user.getId());
            }
        }

        //匹配组织类型名称
        if(StringPlusUtils.isBlank(organization.getTypeName())){
            return 0;
        }else{
            List<Dictionary> orgTypes=DictUtils.getByDictionaryName("组织类型");
            for (Dictionary type:orgTypes) {
                if(organization.getTypeName().equals(type.getDetailName())){
                    organization.setTypeId(type.getId());
                }
            }
            if(null==organization.getTypeId()){
                return 0;
            }
        }

        //匹配上级组织
        if(StringPlusUtils.isNotBlank(organization.getParentName())){
            Organization orgParent=getByName(organization.getParentName());
            if(null==orgParent){
                return 0;
            }else {
                organization.setParentId(orgParent.getId());
            }
        }

        //匹配省市区
        if(StringPlusUtils.isNotBlank(organization.getDistrict())){
            Region region= regionService.getByNameAndParentName(organization.getDistrict(), organization.getCity());
            if(null==region){
                logger.warn("导入失败：区无法匹配");
                return 0;
            }else{
                organization.setDistrictId(region.getId());
            }
        }else {
            logger.warn("导入失败：区不能为空");
            return 0;
        }

        if(StringPlusUtils.isNotBlank(organization.getCity())){
            Region region= regionService.getByNameAndParentName(organization.getCity(), organization.getProvince());
            if(null==region){
                logger.warn("导入失败：市无法匹配");
                return 0;
            }else{
                organization.setCityId(region.getId());
            }
        }else {
            logger.warn("导入失败：市不能为空");
            return 0;
        }

        if(StringPlusUtils.isNotBlank(organization.getProvince())){
           Region region= regionService.getByName(organization.getProvince());
            if(null==region){
                logger.warn("导入失败：省无法匹配");
                return 0;
            }else{
                organization.setProvinceId(region.getId());
            }
        }else {
            logger.warn("导入失败：省不能为空");
            return 0;
        }



        return save(organization);
    }

    /**
     * 根据门店id生成分享token
     * @param orgId 门店id（组织id）
     * @return
     */
    public ResponseResult getShareToken(Long orgId){
        Preconditions.checkNotNull(orgId,"组织id不能为空");
        Organization organization = get(orgId);
        if(null!=organization){
            ShareToken token = new ShareToken();
            token.setOrgId(orgId);

            Map<String,String> rs=Maps.newHashMap();
            rs.put("token", ShareTokenUtils.getToken(token));
            return ResponseResult.ok(rs);
        }
        else {
            return ResponseResult.failByBusiness("组织id无效");
        }
    }

    /**
     * 根据token获取门店信息以及其下可用优惠券
     * @param organization 封装门店坐标
     * @param shareToken 门店分享token
     * @return
     */
    public ResponseResult getByShareToken(Organization organization, String shareToken){
        Preconditions.checkNotNull(shareToken,"分享token不能为空");
        ShareToken share = ShareTokenUtils.cheakToken(shareToken);

        Organization org=get(share.getOrgId());
        ResponseResult rs=couponService.findCouponsByOrgId(org.getId());
        logger.info(rs.toString());

        Map<String,Object> couponInfo= (Map<String, Object>) rs.getBody().get("data");
        logger.info(couponInfo.toString());
        org.setCoupons((List<Coupon>) couponInfo.get("data"));

        org.setReceiveNum((Integer) couponInfo.get("receiveNum"));

        //计算距离用户距离
        if(null!=organization && null!=organization.getLat() && null!=organization.getLng()){
            org.setDistance(GeoUtils.getDistanceByPoint(organization.getLng().doubleValue(), organization.getLat().doubleValue()
                    , org.getLng().doubleValue(), org.getLat().doubleValue()));
        }
        return  ResponseResult.ok(org);
    }
}
