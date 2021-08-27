package com.josipven.test;

import com.josipven.dao.CustomerDao;
import com.josipven.dao.LinkManDao;
import com.josipven.domain.Customer;
import com.josipven.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author: Josipven
 * @date: 8/27/21 2:01 PM
 * @description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ObjectQueryTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;


    /**
     * 查询一个对象的时候，查询他的所有关联对象
     *
     * 对象导航查询：默认使用延迟加载的形式查询
     *              调用get不会立即发送查询，而是在使用关联对象的时候才会查询
     * 修改配置，将延迟加载改为立即加载
     *              fetch：需要配置到多表映射关系的注解上
     */
    @Test
    @Transactional //解决could not initialize proxy - no Session
    public void testQuery1() {
        //
        Customer customer = customerDao.findOne(2L);
        //对象导航查询，此客户的所有联系人
        Set<LinkMan> linkMEN = customer.getLinkMEN();

        for (LinkMan man : linkMEN) {
            System.out.println(man);
        }

    }


    /**
     *
     */

}

