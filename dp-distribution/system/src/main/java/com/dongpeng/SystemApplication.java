package com.dongpeng;

import com.dongpeng.system.listener.SystemApplicationStartup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication springApplication=new SpringApplication(SystemApplication.class);
        springApplication.addListeners(new SystemApplicationStartup());//加载system项目启动监听器
        springApplication.run(args);
        /*SpringApplication.run(SystemApplication.class, args);*/
    }


}
