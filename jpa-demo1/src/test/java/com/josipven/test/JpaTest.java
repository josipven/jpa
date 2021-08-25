package com.josipven.test;

import com.josipven.domain.Customer;
import com.josivpen.utils.JpaUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author: Josipven
 * @date: 8/25/21 1:10 PM
 * @description:
 */
public class JpaTest {

//    @Autowired
//    private Customer customer;

    @Test
    public void testSave() {

        /*
        //1.加载配置文件创建工厂（实体管理器工厂）对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myJpa");

        //2.通过实体管理器工厂获取实体管理器
        EntityManager entityManager = factory.createEntityManager();
        */

        EntityManager entityManager = JpaUtils.getEntityManager();

        //3.获取事务对象，开启事务
        EntityTransaction transaction = entityManager.getTransaction();//获取事务对象
        transaction.begin();//开启事务

        //4.完成增删改查操作：保存一个客户到数据库中
        Customer customer = new Customer();
        customer.setCustName("传智播客");
        customer.setCustIndustry("教育");

        //保存
        entityManager.persist(customer); //保存操作

        //5.提交事务
        transaction.commit();

        //6.释放资源
        entityManager.close();

//        factory.close();
    }


    /**
     * 根据id查找客户
     */
    @Test
    public void testFind() {
        //1、通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、增删改查 --> 根据id查找客户
        /**
         * find：根据id查对象
         *      class: 查询数据的结果需要包装的实体类类型的字节码
         */
        /**
         * getReference方法：
         *      （懒加载）
         *      1、获取的对象是一个动态代理对象
         *      2、调用getReference方法不会立刻发送sql语句查询数据库；当调用查询结果对象的时候，才会发送查询的sql语句，什么时候用，什么时候发送sql语句
         *
         */
        //        Customer customer = entityManager.find(Customer.class, 1L);
        Customer customer = entityManager.getReference(Customer.class, 1L);
        System.out.println(customer);

        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();

    }

    /**
     * 根据id删除客户
     */
    @Test
    public void testRemove() {
        //1、通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、增删改查 --> 根据id删除客户
        /**
         * 3.1根据id查询客户
         * 3.2调用remove方法删除客户
         */
        Customer customer = entityManager.find(Customer.class, 1L);
        entityManager.remove(customer);

        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();

    }


    /**
     * 根据id更新客户
     */
    @Test
    public void testUpdate() {
        //1、通过工具类获取entityManager
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、增删改查 --> 根据id更新客户
        /**
         * 3.1根据id查询客户
         * 3.2调用remove方法删除客户
         */
        Customer customer = entityManager.getReference(Customer.class, 2L);
        customer.setCustIndustry("IT");


        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();

    }
}

