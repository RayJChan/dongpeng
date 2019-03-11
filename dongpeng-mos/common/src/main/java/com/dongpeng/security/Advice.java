package com.dongpeng.security;

import com.dongpeng.common.config.Global;
import com.dongpeng.common.utils.J2CacheUtils;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Set;

public class Advice implements MethodInterceptor {
    private void pemissionsVerify(RequiresPermissions requiresPermissions) throws UnauthorizedException {
        ServletRequestAttributes requestAttributes= (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        String userId=requestAttributes.getRequest().getHeader(Global.SECURITY_TOKEN_USERID);//从zuul header中获取用户id
        String permissionsString=(String)J2CacheUtils.get(Global.USER_MENU_REGION, userId);//从缓存中获取用户权限集合字符串
        if (Strings.isNullOrEmpty(permissionsString)){
            throw new UnauthorizedException();
        }
        Set<String> permissions = new Gson().fromJson(permissionsString,new TypeToken<Set<String>>(){}.getType());
        String[] requiresPermission=requiresPermissions.value();
        if (requiresPermissions.logical()==Logical.AND){
            for (String permission : requiresPermission){
                if (permissions.add(permission)){
                    throw new UnauthorizedException();
                }
            }
        }
        else if (requiresPermissions.logical()==Logical.OR){
            for (String permission : requiresPermission){
                if (!permissions.add(permission)){
                    return;
                }
            }
        }
    }

    private void rolesVerify(RequiresRoles requiresRoles) throws UnauthorizedException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String rolesString= requestAttributes.getRequest().getHeader(Global.HEADER_ROLES);
        if (Strings.isNullOrEmpty(rolesString)){
            throw new UnauthorizedException();
        }
        Set<String> roles = new Gson().fromJson(rolesString,new TypeToken<Set<String>>(){}.getType());
        String[] requiresRole = requiresRoles.value();
        if (requiresRoles.logical()==Logical.AND){
            for (String role:requiresRole){
                if (roles.add(role)){
                    throw new UnauthorizedException();
                }
            }
        }
        else if (requiresRoles.logical()==Logical.OR){
            for (String role:requiresRole){
                if (!roles.add(role)){
                    return;
                }
            }
        }
    }

    @Override
    public Object invoke(org.aopalliance.intercept.MethodInvocation methodInvocation) throws Throwable {
        Optional<RequiresPermissions> permissionsOptional = Optional.fromNullable(methodInvocation.getMethod().getAnnotation(RequiresPermissions.class)).or(Optional.fromNullable(methodInvocation.getMethod().getDeclaringClass().getAnnotation(RequiresPermissions.class)));
        if (permissionsOptional.isPresent()) {
            pemissionsVerify(permissionsOptional.get());
        }
        Optional<RequiresRoles> rolesOptional = Optional.fromNullable(methodInvocation.getMethod().getAnnotation(RequiresRoles.class)).or(Optional.fromNullable(methodInvocation.getMethod().getDeclaringClass().getAnnotation(RequiresRoles.class)));
        if (rolesOptional.isPresent()) {
            rolesVerify(rolesOptional.get());
        }
        return methodInvocation.proceed();
    }
}