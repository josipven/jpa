package com.josipven.test;

import com.josipven.dao.RoleDao;
import com.josipven.dao.UserDao;
import com.josipven.domain.Role;
import com.josipven.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Josipven
 * @date: 8/27/21 1:40 PM
 * @description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ManyToManyTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 多对多的一方，被选择的一方放弃维护权
     * （角色放弃维护权）
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testAdd() {
        //
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序员");

        //配置用户到角色的关系
        user.getRoles().add(role);
        //配置角色到用户的关系
        role.getUsers().add(user);

        userDao.save(user);
        roleDao.save(role);

    }


    /**
     * 级联添加：保存用户的同时，保存角色
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeAdd() {
        //
        User user = new User();
        user.setUserName("小李");

        Role role = new Role();
        role.setRoleName("java程序员");

        //配置用户到角色的关系
        user.getRoles().add(role);

        userDao.save(user);
    }


    /**
     * 级联删除：删除用户id为1的同时，删除关联对象
     */
    @Test
    @Transactional
    @Rollback(value = false)
    public void testCascadeRemove() {
        //1、查询
        User user = userDao.findOne(1L);
        //2、删除
        userDao.delete(user);
    }

}

