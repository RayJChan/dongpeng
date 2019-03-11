package com.dongpeng.system;

import com.dongpeng.common.utils.J2CacheUtils;
import net.oschina.j2cache.CacheProviderHolder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SystemApplicationTests {

    @Test
    public void test() {
        System.out.println("sfsdf");
        //CacheProviderHolder.getL2Provider().regions().forEach(e->J2CacheUtils.cache.clear(e.getName()));
        String test="17777161709";
        for (char s:test.toCharArray()) {
            System.out.println(s);
        }
    }
}
