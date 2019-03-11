package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.*;
import com.dongpeng.system.dao.ClientRecordDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional(readOnly = true)
public class ClientRecordService extends BaseCrudService<ClientRecordDao, ClientRecord> {
    @Override
    public String createDataScopeSql(ClientRecord entity) {
        return null;
    }

    public List<ClientRecord> findByRegion(RegionClient regionClient) {
        return dao.findByRegion(regionClient);
    }

    /**
     * 新增一条客户数据
     * @param clientRecord
     * @return
     * @throws OptimisticLockException
     */
    @Transactional(readOnly = false)
    public ResponseResult insert(ClientRecord clientRecord) throws OptimisticLockException {
        ClientRecord clientCode =  dao.getByClientCode(clientRecord.getClientCode());
        if(clientCode != null){
            return ResponseResult.failByParam("客编:["+clientRecord.getClientCode()+"]为已存在有效数据");

        }
        ClientRecord isDelete = dao.getByClientIsDelete(clientRecord.getClientCode());
        if(isDelete != null){
            return ResponseResult.failByParam("客编:["+clientRecord.getClientCode()+"]为已存在无效数据");

        }
        Department department = dao.getByDepartment(clientRecord.getDepartmentId());
        if(department == null){
            return ResponseResult.failByParam("部门:["+clientRecord.getDepartmentId()+"]无效");
        }
        Company company = dao.getCompany(clientRecord.getCompanyId());
        if(company == null){
            return ResponseResult.failByParam("公司:["+clientRecord.getCompanyId()+"]无效");
        }
        Dictionary ClientChannel = dao.getByDictionary(clientRecord.getChannelId());
        if(ClientChannel == null){
            return ResponseResult.failByParam("渠道:["+clientRecord.getChannelId()+"]无效");
        }
        Dictionary ClientType = dao.getByDictionary(clientRecord.getClientTypeId());
        if(ClientType == null){
            return ResponseResult.failByParam("客户类型:["+clientRecord.getClientTypeId()+"]无效");
        }

        if(clientRecord.getProvinceId() != null){
            Region region = dao.getByProvinceId(clientRecord.getProvinceId());
            if(region == null){
                return ResponseResult.failByParam("省:["+clientRecord.getProvinceId()+"]无效");
            }
            clientRecord.setProvince(region.getName());
        }
        if(clientRecord.getCityId() != null){
            Region city = dao.getByProvinceId(clientRecord.getCityId());
            if(city == null){
                return ResponseResult.failByParam("市:["+clientRecord.getCityId()+"]无效");
            }
            clientRecord.setCity(city.getName());
        }
        if(clientRecord.getDistrictId() != null){
            Region district = dao.getByProvinceId(clientRecord.getDistrictId());
            if(district == null){
                return ResponseResult.failByParam("区:["+clientRecord.getDistrictId()+"]无效");
            }
            clientRecord.setDistrict(district.getName());
        }
        clientRecord.setCompanyName(company.getCompanyName());
        clientRecord.setDepartmentName(department.getDepartmentName());
        clientRecord.setChannelName(ClientChannel.getDetailName());
        clientRecord.setClientType(ClientType.getDetailName());
        int rs=this.save(clientRecord);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

    /**
     * 修改客户数据
     * @param clientRecord
     * @return
     */
    @Transactional(readOnly = false)
    public ResponseResult update(ClientRecord clientRecord) throws Exception{
        ClientRecord temp =  dao.getById(clientRecord.getId());
        if(temp == null){
            return ResponseResult.failByParam("id:"+clientRecord.getId()+"无效");
        }
        try {
            clientRecord = share(clientRecord);
        }catch (Exception e){
            return ResponseResult.failByParam(e.getMessage());
        }
        BeanUtils.copyBeanNotNull2Bean(clientRecord, temp);//将非NULL值覆盖temp中的值
        int rs=this.save(clientRecord);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }


    /**
     *财务数据维护
     * @param clientRecord
     * @return
     */
    @Transactional(readOnly = false)
    public ResponseResult maintain(ClientRecord clientRecord) throws Exception {
        ClientRecord temp =  dao.getById(clientRecord.getId());
        if(temp == null){
            return ResponseResult.failByParam("id:"+clientRecord.getId()+"无效");
        }
        try {
            clientRecord = share(clientRecord);
        }catch (Exception e){
            return ResponseResult.failByParam(e.getMessage());
        }
        /*启动积分积分百分比*/
        if(clientRecord.getIntegral()){
            if(clientRecord.getIntegralPercent() == null || "".equals(clientRecord.getIntegralPercent())){
                return ResponseResult.failByParam("积分百分比为空");
            }
            try{
                int IntegralPercent = Integer.valueOf(clientRecord.getIntegralPercent());
                if(IntegralPercent < 0 || IntegralPercent > 100){
                    return ResponseResult.failByParam("积分百分比:]"+clientRecord.getIntegralPercent()+"]有误,正确为0到100之间的数字");
                }
            }catch (Exception e){
                return ResponseResult.failByParam("积分百分比包含特殊字符,正确为:0-100之间");
            }
            if(clientRecord.getIntegralConversion() < 0 || clientRecord.getIntegralConversion() > 1){
                return ResponseResult.failByParam("积分换算:["+clientRecord.getIntegralPercent()+"]有误,正确为0(包含)到1(包含)之间的数字");
            }
        }
        /*启动授信判断必填项*/
        if(clientRecord.getCredit()){
            if(clientRecord.getCreditPercent() == null || "".equals(clientRecord.getCreditPercent())){
                return ResponseResult.failByParam("授信百分比为空");
            }
            try{
                int CreditPercent = Integer.valueOf(clientRecord.getCreditPercent());
                if(CreditPercent < 0 || CreditPercent > 100){
                    return ResponseResult.failByParam("授信百分比:"+clientRecord.getCreditPercent()+"有误,正确为0到100之间的数字");
                }
            }catch (Exception e){
                return ResponseResult.failByParam("授信百分比包含特殊字符,正确为:0-100之间");
            }

            if(clientRecord.getCreditStarttime() == null || "".equals(clientRecord.getCreditStarttime())){
                return ResponseResult.failByParam("授信开始时间为空");
            }
            if(clientRecord.getCreditEndtime() == null || "".equals(clientRecord.getCreditEndtime())){
                return ResponseResult.failByParam("授信结束时间为空");
            }
            if(clientRecord.getCreditlimes() == null || "".equals(clientRecord.getCreditlimes())){
                return ResponseResult.failByParam("授信额度为空");
            }
            if(clientRecord.getInterest() == null || "".equals(clientRecord.getInterest())){
                return ResponseResult.failByParam("日利息为空");
            }
            if(clientRecord.getInterestStarttime() == null){
                return ResponseResult.failByParam("利息开始时间为空");
            }
        }

        BeanUtils.copyBeanNotNull2Bean(clientRecord, temp);//将非NULL值覆盖temp中的值

        int rs=this.save(clientRecord);
        if(1==rs){
            return ResponseResult.ok(null);
        }else{
            return ResponseResult.failByBusiness("保存失败");
        }
    }

    @Transactional(readOnly = false)
    public int saveForExcel(ClientRecord clientRecord) throws Exception {
        try {
            if(StringUtils.isNotBlank(clientRecord.getClientCode())) {
                ClientRecord byShopCode = dao.getByClientCode(clientRecord.getClientCode());
                clientRecord.setCurrentUser();
                if (byShopCode != null) {
                    clientRecord.preUpdate();
                    dao.update(clientRecord);
                    return 1;
                } else {
                    clientRecord.preInsert();
                    dao.insert(clientRecord);
                    return 1;
                }
            }
        }catch (Exception e){
            return 0;
        }
        return 0;
    }

    /**
     * 根据部门名称查询部门
     * @param departmentName
     * @return
     */
    public Department getByDepartmentName(String departmentName) {
       return dao.getByDepartmentName(departmentName);
    }

    /**
     * 根据公司名称查询公司
     * @param companyName
     * @return
     */
    public Company getCompanyName(String companyName) {
        return dao.getCompanyName(companyName);
    }

    /**
     * 查询字典项客户类型
     * @param name
     * @return
     */
    public Dictionary getByDictionaryName(String name) {
        return dao.getByDictionaryName(name);
    }

    public ClientRecord share(ClientRecord clientRecord)throws Exception{

        ClientRecord temp =  dao.getById(clientRecord.getId());
        if(temp == null){
            throw new Exception("id:["+clientRecord.getId()+"]无效");
        }
        if(!clientRecord.getClientCode().equals(temp.getClientCode())){
            ClientRecord clientCode =  dao.getByClientCode(clientRecord.getClientCode());
            if(clientCode != null){
                throw new Exception("客编:["+clientRecord.getClientCode()+"]已有有效数据");

            }
            ClientRecord isDelete = dao.getByClientIsDelete(clientRecord.getClientCode());
            if(isDelete != null){
                throw new Exception("客编:["+clientRecord.getClientCode()+"]已有无效数据");

            }
        }
        Department department = dao.getByDepartment(clientRecord.getDepartmentId());
        if(department == null){
            throw new Exception("部门:["+clientRecord.getDepartmentId()+"]无效");
        }
        Company company = dao.getCompany(clientRecord.getCompanyId());
        if(company == null){
            throw new Exception("公司:["+clientRecord.getCompanyId()+"]无效");
        }
        Dictionary ClientChannel = dao.getByDictionary(clientRecord.getChannelId());
        if(ClientChannel == null){
            throw new Exception("渠道:["+clientRecord.getChannelId()+"]无效");

        }
        Dictionary ClientType = dao.getByDictionary(clientRecord.getClientTypeId());
        if(ClientType == null){
            throw new Exception("客户类型:["+clientRecord.getClientTypeId()+"]无效");

        }
        if(clientRecord.getProvinceId() != null){
            Region region = dao.getByProvinceId(clientRecord.getProvinceId());
            if(region == null){
                throw new Exception("省:["+clientRecord.getProvinceId()+"]无效");
            }
            clientRecord.setProvince(region.getName());
        }
        if(clientRecord.getCityId() != null){
            Region city = dao.getByProvinceId(clientRecord.getCityId());
            if(city == null){
                throw new Exception("市:["+clientRecord.getCityId()+"]无效");
            }
            clientRecord.setCity(city.getName());
        }
        if(clientRecord.getDistrictId() != null){
            Region district = dao.getByProvinceId(clientRecord.getDistrictId());
            if(district == null){
                throw new Exception("区:["+clientRecord.getDistrictId()+"]无效");
            }
            clientRecord.setDistrict(district.getName());
        }
        clientRecord.setCompanyName(company.getCompanyName());
        clientRecord.setDepartmentName(department.getDepartmentName());
        clientRecord.setChannelName(ClientChannel.getDetailName());
        clientRecord.setClientType(ClientType.getDetailName());
        return clientRecord;
    }

    /**
     * 根据名称查询省市区
     * @param name
     * @return
     */
    public Region getByProvinceName(String name) {
    return dao.getByProvinceName(name);
    }
}
