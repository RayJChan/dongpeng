package com.dongpeng.system.controller;

import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.InterfaceRecord;
import com.dongpeng.system.service.InterfaceRecordService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * 接口档案 管理接口 controller
 */
@RestController
@RequestMapping("/interfaceRecord")
public class InterfaceRecordController extends BaseDataController<InterfaceRecordService,InterfaceRecord> {

    @Override
    @EnableDetailLog(serviceclass = InterfaceRecordService.class,handleName = "更新接口档案")
    public ResponseResult update(@RequestBody InterfaceRecord interfaceRecord, HttpServletRequest request, Model model) throws Exception {
        return super.update(interfaceRecord, request, model);
    }
}
