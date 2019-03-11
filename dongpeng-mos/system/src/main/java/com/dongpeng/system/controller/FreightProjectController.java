package com.dongpeng.system.controller;

import com.dongpeng.common.db.annotation.EnableDetailLog;
import com.dongpeng.common.db.contorller.BaseDataController;
import com.dongpeng.common.db.exception.OptimisticLockException;
import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.entity.system.FreightProject;
import com.dongpeng.system.service.FreightProjectService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * 运费项目管理接口controller
 */
@RestController
@RequestMapping("/freightProject")
public class FreightProjectController extends BaseDataController<FreightProjectService,FreightProject> {

    @Override
    @EnableDetailLog(serviceclass =FreightProjectService.class,handleName = "更新一个运费项目")
    public ResponseResult update(@RequestBody FreightProject freightProject, HttpServletRequest request, Model model) throws Exception {
        return super.update(freightProject, request,model);
    }
}
