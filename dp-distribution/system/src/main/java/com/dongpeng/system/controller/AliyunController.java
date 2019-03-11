package com.dongpeng.system.controller;

import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.OssStsUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 阿里云相关接口 controller
 */
@RestController
@RequestMapping("/aliyun")
public class AliyunController {

    /**
     * 获取oss token
     * @return
     */
    @GetMapping("/oss/getToken")
    public ResponseResult getOssToken(){
        return ResponseResult.ok(OssStsUtils.getOssTemporary2());
    }
}
