package com.dongpeng.system.service;

import com.alibaba.fastjson.JSON;
import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.Page;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.StringPlusUtils;
import com.dongpeng.common.vo.AddressVo;
import com.dongpeng.entity.system.ClientRecord;
import com.dongpeng.entity.system.Region;
import com.dongpeng.entity.system.RegionClient;
import com.dongpeng.system.dao.RegionDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,rollbackFor = Exception.class)
public class RegionService extends BaseCrudService<RegionDao, Region> {
    @Override
    public String createDataScopeSql(Region entity) {
        return null;
    }
    public String createDataScopeSql(AddressVo addressVo) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(Region region) throws OptimisticLockException {
        //如果父id不为空，则获取其名称、parentids
        if (null != region.getParentId() && 0 != region.getParentId()) {
            Region parent = dao.get(region.getParentId());
            region.setParentName(parent.getName());
            region.setParentIds(
                    StringUtils.isNotBlank(parent.getParentIds()) ?
                            parent.getParentIds() + "," + parent.getId()
                            : parent.getId().toString());
        }
        int rs = super.save(region);
        if (0 < rs && !region.getIsNewRecord()) {
            //更新其子节点parentids
            dao.updateParentids(region);
        }
        return rs;
    }

    /**
     * 根据名称查找唯一
     *
     * @param region 封装 省、市、区 数据
     * @return
     */
    public Region getByName(Region region) {
        return dao.getByName(region);
    }
    /**
     * 查找城市列表
     *
     * @param region
     * @return
     */
    public List<Region> findCityList(Region region) {
        /**
         * 只选择城市
         */
        region.setLevel(2);
        List<Region> regions = findList(region);
        return regions;
    }

    public Page<AddressVo> findDistrictList(Page<AddressVo> addressVoPage, AddressVo addressVo) {
        Region region = new Region();
        // 省
        if (addressVo.getProvince() != null && addressVo.getProvince() != "") {
            String regionId = dao.getIdByName(addressVo.getProvince());
            region.setParentIds(regionId);
        }
        // 市
        if (addressVo.getCity() != null && addressVo.getCity() != "") {
            region.setParentName(addressVo.getCity());
        }
        // 区/县
        if (StringUtils.isNotEmpty(addressVo.getDistrict())) {
            region.setName(addressVo.getDistrict());
        }
        region.setDeleteFlag(addressVo.isDeleteFlag());
        /**
         *  只选择区/县
         */
        region.setLevel(3);
        List<Region> regionList = dao.findDistrictList(region);
        List<AddressVo> addressVoList = new ArrayList<>();
        for (Region regions : regionList) {
            AddressVo address = new AddressVo();
            Region cityRegin = dao.getParentNameById(regions.getParentId());
            address.setId(regions.getId());
            address.setDistrict(regions.getName());
            address.setCreaterName(regions.getCreaterName());
            address.setCreateTime(regions.getCreateTime());
            address.setModifierName(regions.getModifierName());
            address.setModifyTime(regions.getModifyTime());
            address.setCity(regions.getParentName());
            address.setCityId(regions.getParentId());
            address.setProvince(cityRegin.getParentName());
            address.setProvinceId(cityRegin.getParentId());
            addressVoList.add(address);
        }
        addressVo.setPage(addressVoPage);
        addressVoPage.setList(addressVoList);
        return addressVoPage;
    }

    /**
     * 查找服务范围
     *
     * @return
     */
    public List<Region> findServiceArea() {
        List<Region> regions = dao.findAllProvinceAndCity(new Region());

        Map<Long, Region> countrys = Maps.newHashMap();
        Map<Long, Region> provinces = Maps.newHashMap();
        Map<Long, Region> citys = Maps.newHashMap();

        for (Region re : regions) {
            re.setLabel(re.getName());
            re.setChildren(null);//避免缓存导致重复子集

            switch (re.getLevel()) {
                case 0:
                    countrys.put(re.getId(), re);
                    break;
                case 1:
                    provinces.put(re.getId(), re);
                    if (countrys.containsKey(re.getParentId())) {
                        if (StringPlusUtils.isBlankList(countrys.get(re.getParentId()).getChildren())) {
                            countrys.get(re.getParentId()).setChildren(Lists.newArrayList());
                        }
                        countrys.get(re.getParentId()).getChildren().add(re);
                    }
                    break;
                case 2:
                    citys.put(re.getId(), re);
                    if (provinces.containsKey(re.getParentId())) {
                        if (StringPlusUtils.isBlankList(provinces.get(re.getParentId()).getChildren())) {
                            provinces.get(re.getParentId()).setChildren(Lists.newArrayList());
                        }
                        provinces.get(re.getParentId()).getChildren().add(re);
                    }
                    break;
                case 3:
                    if (citys.containsKey(re.getParentId())) {
                        if (StringPlusUtils.isBlankList(citys.get(re.getParentId()).getChildren())) {
                            citys.get(re.getParentId()).setChildren(Lists.newArrayList());
                        }
                        citys.get(re.getParentId()).getChildren().add(re);
                    }
                    break;
            }
        }
        return Lists.newArrayList(countrys.values());

        /*for (Region re:regions) {
            if(1==re.getLevel()){
                re.setLabel(re.getName());
                re.setChildren(null);//避免缓存导致重复子集
                serviceAreas.add(re);
            }else if(2==re.getLevel()){
                for (Region province:serviceAreas) {
                    if(StringPlusUtils.isNotBlank(province.getProvince()) && province.getProvince().equals(re.getProvince())){
                        if(StringPlusUtils.isBlankList(province.getChildren())){
                            province.setChildren(Lists.newArrayList());
                        }
                        re.setLabel(re.getCity());
                        province.getChildren().add(re);
                        break;
                    }
                }
            }
        }*/
        //logger.warn(JsonMapper.toJsonString(serviceAreas));
        //return serviceAreas;
    }
    /**
     * 从excel导入插入数据
     *
     * @param region
     * @return
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public int saveForExcel(Region region) throws OptimisticLockException {
        if (StringUtils.isNotBlank(region.getParentName())) {
            Region parent = dao.getByName(region);
            if (null != parent) {
                region.setParentId(parent.getId());
            } else {
                return 0;//该条数据插入失败
            }
        }
        return save(region);
    }

    /**
     * 逻辑删除/恢复，更新delete_flag字段为1/0,在表包含字段del_flag时，可以调用此方法，将数据隐藏/显示）
     *
     * @param region
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteToggle(Region region) {
        Region regionTemp = get(region.getId());//根据id查找region，加载出parentId
        if (false == region.getDeleteFlag() && null != regionTemp.getParentId()) {//如果启用，且父节点不为空，则要判断父节点是否被禁用，父节点被禁用时不能执行启用操作
            Region parent = get(regionTemp.getParentId());
            if (true == parent.getDeleteFlag()) {
                return 0;
            }
        }
        return dao.deleteToggle(region);
    }

    /**
     *更新省市区
     * @param addressVo
     * @return
     */
    @Transactional
    public ResponseResult updateList(AddressVo addressVo) {
        Region provinceRegion = dao.get(addressVo.getProvinceId());
        logger.info("入参："+addressVo.getProvince()+"---数据库:"+provinceRegion.getName());
        if(!provinceRegion.getName().equals(addressVo.getProvince())){
            provinceRegion.setName(addressVo.getProvince());
            provinceRegion.setCurrentUser();
            provinceRegion.preUpdate();
            try{
                dao.updateByPrimaryKeySelective(provinceRegion);
            }catch (Exception e){
                logger.info("省名称更新失败："+e.getMessage());
            }
        }
        Region cityRegion = dao.get(addressVo.getCityId());
        logger.info("入参："+addressVo.getCity()+"---数据库:"+cityRegion.getName());
        if(!cityRegion.getName().equals(addressVo.getCity())){
            cityRegion.setParentName(provinceRegion.getName());
            cityRegion.setName(addressVo.getCity());
            cityRegion.setCurrentUser();
            cityRegion.preUpdate();
            try{
                dao.updateByPrimaryKeySelective(cityRegion);
            }catch (Exception e){
                logger.info("市名称更新失败："+e.getMessage());
            }
        }
        Region districtRgion = dao.get(addressVo.getId());
        logger.info("入参："+addressVo.getDistrict()+"---数据库:"+districtRgion.getName());
        if(!districtRgion.getName().equals(addressVo.getDistrict())){
            districtRgion.setParentName(provinceRegion.getName());
            districtRgion.setDeleteFlag(addressVo.getDeleteFlag());
            districtRgion.setName(addressVo.getDistrict());
            districtRgion.setCurrentUser();
            districtRgion.preUpdate();
            try{
                dao.updateByPrimaryKeySelective(districtRgion);
            }catch (Exception e){
                logger.info("区名称更新失败："+e.getMessage());
            }
        }
        return ResponseResult.ok();
    }

    public List<AddressVo> findExportFile(AddressVo addressVo) {
        Region region = new Region();
        // 省
        if (addressVo.getProvince() != null && addressVo.getProvince() != "") {
            String regionId = dao.getIdByName(addressVo.getProvince());
            region.setParentIds(regionId);
        }
        // 市
        if (addressVo.getCity() != null && addressVo.getCity() != "") {
            region.setParentName(addressVo.getCity());
        }
        // 区/县
        if (StringUtils.isNotEmpty(addressVo.getDistrict())) {
            region.setName(addressVo.getDistrict());
        }
        region.setDeleteFlag(addressVo.isDeleteFlag());
        /**
         * 只选择区/县
         */
        region.setLevel(3);
        List<AddressVo> addressVoList = new ArrayList<>();
        List<Region> regionList = dao.findDistrictList(region);
            for (Region regions : regionList) {
                AddressVo address = new AddressVo();
                Region cityRegin = dao.getParentNameById(regions.getParentId());
                address.setId(regions.getId());
                address.setDistrict(regions.getName());
                address.setCreaterName(regions.getCreaterName());
                address.setCreateTime(regions.getCreateTime());
                address.setModifierName(regions.getModifierName());
                address.setModifyTime(regions.getModifyTime());
                address.setCity(regions.getParentName());
                address.setCityId(regions.getParentId());
                address.setProvince(cityRegin.getParentName());
                address.setProvinceId(cityRegin.getParentId());
                addressVoList.add(address);
            }
        return addressVoList;
    }
}
