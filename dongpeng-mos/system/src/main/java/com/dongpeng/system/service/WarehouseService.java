package com.dongpeng.system.service;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.entity.system.Company;
import com.dongpeng.entity.system.Warehouse;
import com.dongpeng.entity.system.UserWarehouse;
import com.dongpeng.system.dao.WarehouseDao;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class WarehouseService extends BaseCrudService<WarehouseDao,Warehouse> {
    @Autowired
    private CompanyService companyService;

    @Override
    @Transactional(readOnly = false)
    public int save(Warehouse warehouse) throws OptimisticLockException {
        //如果父id不为空，则获取其parentids
        if(null!=warehouse.getParentId() && 0!=warehouse.getParentId()){
            Warehouse parentWarehouse=dao.get(warehouse.getParentId());
            warehouse.setParentName(parentWarehouse.getWarehouseName());
            warehouse.setParentIds(
                    StringUtils.isNotBlank(parentWarehouse.getParentIds())?
                            parentWarehouse.getParentIds()+","+parentWarehouse.getId()
                            :parentWarehouse.getId().toString());
        }
        //如果公司id不为空，公司名称为空，自动查找补全公司名称
        if(null!=warehouse.getCompanyId() && StringUtils.isBlank(warehouse.getCompanyName())){
            Company company=companyService.get(warehouse.getCompanyId());
            if(null!=company){
                warehouse.setCompanyName(company.getCompanyName());
            }
        }
        return super.save(warehouse);
    }

    /**
     * 根据上级id获得仓库
     * @param upId 上级id
     * @return
     */
    public List<Warehouse> getByUp(Long upId){
        return dao.getByUp(upId);
    }

    /**
     * 根据根据仓库编码获得仓库
     * @param warehouseCode 仓库编码
     * @return
     */
    public Warehouse getByWarehouseCode(String warehouseCode){
        return dao.getByWarehouseCode(warehouseCode);
    }

    /**
     * 删除用户仓库
     * @param userWarehouse 封装用户id 和其他删除条件参数
     * @return
     */
    @Transactional(readOnly = false)
    public int deleteUserWarehouse(UserWarehouse userWarehouse){
        int rs=dao.deleteUserWarehouse(userWarehouse);
        return rs;
    }

    /**
     * 插入用户仓库
     * @param userId 用户id
     * @param warehouseIds 仓库id集合，多个用逗号分隔
     * @return
     */
    @Transactional(readOnly = false)
    public int insertUserWarehouse(Long userId,String warehouseIds){
        int rs=0;
        List<UserWarehouse> userWarehouses= Lists.newArrayList();
        String idArray[] =warehouseIds.split(",");
        for(String warehouseId : idArray){
            UserWarehouse userWarehouse=new UserWarehouse(userId,Long.valueOf(warehouseId));
            //校验是否重复
            List<Warehouse> warehouses= findListByUserId(userWarehouse);
            if(null==warehouses || warehouses.isEmpty()){
                //设置用户仓库属性
                userWarehouse.setCurrentUser();
                userWarehouse.preInsert();
                userWarehouses.add(userWarehouse);
            }

        }
        if(0<userWarehouses.size()){
            rs=dao.insertUserWarehouse(userWarehouses);
        }
        return rs;
    }

    /**
     *  根据用户id查找仓库信息
     * @param userWarehouse 封装用户id
     * @return
     */
    public List<Warehouse> findListByUserId(UserWarehouse userWarehouse){
        //超级管理员拥有全部仓库权限
        if(1==userWarehouse.getUserId()){
            return dao.findAllList(new Warehouse());
        }else{
            return dao.findListByUserId(userWarehouse);
        }
    }

    /**
     *  根据用户id查找该用户未有的仓库信息
     * @param userWarehouse 封装用户id
     * @return
     */
    public List<Warehouse> findListNotInUserId(UserWarehouse userWarehouse){
        //超级管理员不需要返回
        if(Global.ADMINISTRATOR_ID==userWarehouse.getUserId()){
            return null;
        }else{
            return dao.findListNotInUserId(userWarehouse);
        }
    }

    @Override
    public String createDataScopeSql(Warehouse entity) {
        return null;
    }

    /**
     * 模糊查询对应部门未添加的仓库
     * @param departmentId
     * @param warehouseName
     * @return
     */
    public List<Warehouse> findIsNotAll(String departmentId,String warehouseName) {
        return dao.findIsNotAll(departmentId,warehouseName);
    }
}
