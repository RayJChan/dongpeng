package com.dongpeng.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 密码工具类
 */
public class PasswordUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    public static final int SALT_SIZE = 8;
    public static final int HASH_INTERATIONS = 1024;

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     * @param plainPassword 明文密码
     * @return
     */
    public static String entryptPasswordBySha1(String plainPassword) {
        byte[] salt = DigestsUtils.generateSalt(SALT_SIZE);
        byte[] hashPassword = DigestsUtils.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param password 密文密码
     * @return true : 匹配成功 <br/> false : 匹配失败
     */
    public static boolean validatePasswordBySha1(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0,16));
        byte[] hashPassword = DigestsUtils.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
    }

    /**
     * 生成md5密码
     * @param plainPassword 明文密码
     * @return
     */
    public static String entryptPasswordByMd5(String plainPassword) {
        return DigestsUtils.string2MD5(plainPassword);
    }

    public static void main(String[] args){
        System.out.println(PasswordUtils.entryptPasswordBySha1("admin"));
        System.out.println(PasswordUtils.validatePasswordBySha1("111111", "6cbeb8cc54c2ecbf6f1d068b51c4afaaef0885fda14d7f53c6a63824"));

    }
}
