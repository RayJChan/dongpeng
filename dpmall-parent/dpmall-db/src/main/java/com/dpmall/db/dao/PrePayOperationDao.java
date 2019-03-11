package com.dpmall.db.dao;

import com.dpmall.db.bean.*;
import com.dpmall.db.bean.po.PrePayOperationPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

@MapperScan
public interface PrePayOperationDao {

    /***
     *查看更新状态时的备注
     */
    public PrePayOperationRemarkEntity getRemarkByOperationId(@Param("id") String operationId);

    public int insert(PrePayOperationPo po);

    /***
     *操作状态的时间 与记录
     */
    public List<OtherPrePayOperationDetailEntity> getTimeAndStatus (@Param("prePayId")String prePayId);


}
