package com.dongpeng.common.db.utils;

import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.db.service.OperatingRecordService;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.OperatingRecord;
import net.oschina.j2cache.cache.support.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作记录工具类
 */
public class OperatingRecordUtils {
    private static Logger log = LoggerFactory.getLogger(OperatingRecordUtils.class);
    private static OperatingRecordService operatingRecordService=SpringUtil.getBean(OperatingRecordService.class);

    /**
     * 新增一个操作记录
     * @param operatingRecord 操作记录
     * @return 成功的条目
     */
    public static int addLog(OperatingRecord operatingRecord){
        operatingRecord.setCurrentUser();
        int rs= 0;
        try {
            rs = operatingRecordService.save(operatingRecord);
        } catch (OptimisticLockException e) {
            log.error("添加操作日志失败：", e);
        }
        return rs;
    }

    /**
     * 新增一个操作记录
     * @param operatingRecord 操作记录
     * @param userId 当前用户id
     * @param userName 当前用户名称
     * @return
     */
    public static int addLog(OperatingRecord operatingRecord,Long userId,String userName){
        operatingRecord.setCreaterId(userId);
        operatingRecord.setCreaterName(userName);
        operatingRecord.setModifierId(userId);
        operatingRecord.setModifierName(userName);
        int rs= 0;
        try {
            rs = operatingRecordService.save(operatingRecord);
        } catch (OptimisticLockException e) {
            log.error("添加操作日志失败：", e);
        }
        return rs;
    }
}
