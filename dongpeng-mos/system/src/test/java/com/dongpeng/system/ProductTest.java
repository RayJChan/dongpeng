package com.dongpeng.system;

import com.dongpeng.entity.system.Product;
import com.dongpeng.system.dao.ProductDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@Transactional
public class ProductTest extends SystemApplicationTests {

    @Autowired
    private ProductDao productDao;

    @Test
    public void insert() {
        Product product=new Product();
        product.setBrandId(1l);
        product.setCategoryId(1l);
        product.setCreaterId(1l);
        int result=productDao.insert(product);
        assertEquals(result,1);
    }

    @Test
    public void get(){
        Product product=productDao.get(4l);
        Product deletedProduct=productDao.get(5l);
        assertNotNull(product);
        assertNull(deletedProduct);
    }

    @Test
    public void update(){
        Product product=productDao.get(4l);
        product.setModifierName("谢聪");
        int result=productDao.update(product);
        assertEquals(result,1);
        Product product2=productDao.get(4l);
        product2.setVersion(product2.getVersion()+1);
        result=productDao.update(product2);
        assertEquals(result,0);
    }

   /* @Test
    public void delete(){
        Product product=productDao.get(4l);
        int result=productDao.delete(product);
        assertEquals(result,1);
    }*/

    @Test
    public void deleteFlag(){
        Product product=productDao.get(4l);
        int result=productDao.deleteToggle(product);
        assertEquals(result,1);
    }

}
