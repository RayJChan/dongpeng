package com.dongpeng.system.service;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.BaseCrudService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.BeanUtils;
import com.dongpeng.entity.system.BlRank;
import com.dongpeng.system.dao.BlRankDao;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class BlRankService extends BaseCrudService<BlRankDao,BlRank> {

    @Override
    public String createDataScopeSql(BlRank entity) {
        return null;
    }


    @Override
    @Transactional
    public int save(BlRank blRank) throws OptimisticLockException {
        Preconditions.checkArgument(findUniqueByRankName(blRank.getRankName())==null,"职级名称不能重复");
        if(null!=blRank.getParentId()){
            BlRank parentBlRank =   this.get(blRank.getParentId());
            Preconditions.checkNotNull(parentBlRank,"无法找到ID："+blRank.getParentId()+"的父节点");
            String parentIds = StringUtils.defaultString(parentBlRank.getParentIds(),"");
            parentIds = parentIds + parentBlRank.getId() + ",";
            blRank.setParentIds(parentIds);
        }
        return super.save(blRank);
    }

    @Transactional
    public ResponseResult update(BlRank blRank) throws Exception {
        Preconditions.checkNotNull(blRank.getId(),"id 不能为空");
        BlRank blRankTemp=this.get(blRank.getId());
        Preconditions.checkNotNull(blRankTemp,"id 无效");
        BlRank oldBlRank = findUniqueByRankName(blRank.getRankName());
        if(null!=oldBlRank){
            Preconditions.checkArgument(oldBlRank.getId().equals(blRankTemp.getId()),"职级名称不能重复");
        }
        boolean isUpdate = false;
        String olRrankName = blRankTemp.getRankName();
        Long parentId = blRankTemp.getParentId();
        if(olRrankName.equals(blRank.getRankName())){
            isUpdate = true; //如果名称更改 查看其它父节点 是否需要更新名称
        }
        if(null!=blRank.getParentId()){
            BlRank parent = this.get(blRank.getParentId());
            Preconditions.checkNotNull(parent,"父ID不存在");
            String parentIds = StringUtils.defaultString(parent.getParentIds(),"");
            if(parentIds.length()>0){
                parentIds = parentIds.concat(parent.getId().toString());
                blRank.setParentIds(parentIds.concat(","));
            }else{
                blRank.setParentIds(parent.getId().toString().concat(","));
            }
        }else {
            blRank.setParentIds("");
        }
        BeanUtils.copyBeanNotNull2Bean(blRank, blRankTemp);//将blRank非NULL值覆盖blRankTemp中的值
        int rs = super.save(blRankTemp);
        if(rs==1 && isUpdate){
            Map<String,Object> param=new HashMap<>();
            param.put("parentId",blRank.getId());
            param.put("parentName",blRank.getRankName());
            this.updateParentNameByParentId(param);
        }

       return  1==rs ? ResponseResult.ok(null) : ResponseResult.failByBusiness("更新职级信息失败");
    }



    @Transactional
    public int updateEnable(BlRank blRank){
        blRank.setCurrentUser();
        blRank.preUpdate();
        Long parentId = blRank.getParentId();
        if(null!=parentId){
            BlRank parentBlRank = this.get(parentId);
            if(null!=parentBlRank && !parentBlRank.getEnable()){
                    Preconditions.checkState(parentBlRank.getEnable(),"父节点处于未启用状态，无法直接启用该职级");
            }
        }
        int rs = dao.updateEnable(blRank);
        return rs;
    }

    /**
     * 根据上级ID  更改 上级名称
     * @param map
     * @return
     */
    @Transactional
    public int updateParentNameByParentId (Map<String,Object> map){
       return  dao.updateParentNameByParentId(map);
    }



    public BlRank findUniqueByRankName(String rankName){

        return dao.findUniqueByProperty("rank_name",rankName.trim());
    }

}
