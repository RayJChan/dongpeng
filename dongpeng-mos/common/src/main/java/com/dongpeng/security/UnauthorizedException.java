package com.dongpeng.security;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(){
        super("request is not allowed to access");
    }
}
