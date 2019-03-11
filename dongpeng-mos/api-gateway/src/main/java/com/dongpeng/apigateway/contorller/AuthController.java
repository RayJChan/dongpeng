package com.dongpeng.apigateway.contorller;

import com.dongpeng.common.entity.ResponseResult;
import com.dongpeng.common.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token服务controller
 * <p>暂已取消使用，改为user登录接口生成token</p>
 */
@Deprecated
@RestController
@RequestMapping("/oauth")
public class AuthController {
    @Value("${security.rsa.accessKeys}")
    private String accessKeys;
    @Value("${security.rsa.secretKeys}")
    private String secretKeys;

    @RequestMapping("/token")
    public ResponseResult auth(String accessKey,String secretKey ){
        System.out.println(accessKeys);
        if(StringUtils.isBlank(accessKey) || StringUtils.isBlank(secretKey)){
            return  ResponseResult.failByParam("accessKey or secretKey is null");
        }

        if(!StringUtils.contains(accessKeys, accessKey) || !StringUtils.contains(secretKeys, secretKey)){
            return  ResponseResult.failByParam("key is invalid");
        }

        JWTUtils jwt=JWTUtils.getInstance();
        return ResponseResult.ok(jwt.getToken(accessKey+secretKey));
    }

}
