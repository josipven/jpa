package com.josipven.test;

import com.josipven.dao.CustomerDao;
import com.josipven.dao.LinkManDao;
import com.josipven.domain.Customer;
import com.josipven.domain.LinkMan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Josipven
 * @date: 8/27/21 11:34 AM
 * @description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OneToMany {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private LinkManDao linkManDao;

    /**
     * 保存一个客户，保存一个联系人
     *
     */
    @Test
    @Transactional //配置事务
    @Rollback(value = false) //不自动回滚
    public void testAdd1() {
        //
        Customer customer = new Customer();
        customer.setCustName("baidu");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("xiaoli");

        //配置客户到联系人之间的关系
        customer.getLinkMEN().add(linkMan);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }



    /**
     * 保存一个客户，保存一个联系人
     *
     */
    @Test
    @Transactional //配置事务
    @Rollback(value = false) //不自动回滚
    public void testAdd2() {
        //
        Customer customer = new Customer();
        customer.setCustName("baidu");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("xiaoli");

        //配置联系人到客户之间的关系
        linkMan.setCustomer(customer);
        //配置客户到联系人之间的关系
        customer.getLinkMEN().add(linkMan);

        customerDao.save(customer);
        linkManDao.save(linkMan);
    }


    /**
     * 级联添加：保存一个客户的同时保存客户的所有联系人
     *      需要在操作主体的实体类上，配置cascade属性
     *
     */
    @Test
    @Transactional //配置事务
    @Rollback(value = false) //不自动回滚
    public void testCasCadeAdd() {
        //
        Customer customer = new Customer();
        customer.setCustName("baidu1");

        LinkMan linkMan = new LinkMan();
        linkMan.setLkmName("xiaoli1");

        linkMan.setCustomer(customer);
        customer.getLinkMEN().add(linkMan);

        customerDao.save(customer);
    }



    /**
     * 级联删除：删除1号客户的同时，删除其客户的所有联系人
     *      cascade：配置级联
     */
    @Test
    @Transactional //配置事务
    @Rollback(value = false) //不自动回滚
    public void testCasCadeRemove() {
        //1、查询
        Customer customer = customerDao.findOne(1L);
        //2、删除
        customerDao.delete(customer);
    }


}

