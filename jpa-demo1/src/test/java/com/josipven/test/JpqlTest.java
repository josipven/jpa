package com.josipven.test;

import com.josivpen.utils.JpaUtils;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * @author: Josipven
 * @date: 8/25/21 2:09 PM
 * @description:测试jpql查询
 */
public class JpqlTest {


    /**
     * 查询全部
     *      jpql: from com.josipven.domain.Customer
     *      sql: SELECT * FROM cst_customer
     */
    @Test
    public void testFindAll() {
        //1、获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、查询全部
        String jpql = "from com.josipven.domain.Customer";
        Query query = entityManager.createQuery(jpql);//创建query查询对象，query对象才是执行jpql的对象

        //发送查询，并封装结果集
        List result = query.getResultList();

        for (Object obj : result) {
            System.out.println(obj);
        }

        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();

    }


    /**
     * 根据id倒叙查询全部客户
     *      sql: SELECT * FROM cst_customer ORDER BY cust_id DESC
     *      jpql: from Customer order by custId desc
     *
     */
    @Test
    public void testReverse() {
        //1、获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、查询全部
        String jpql = "from Customer order by custId desc";
        Query query = entityManager.createQuery(jpql);//创建query查询对象，query对象才是执行jpql的对象

        //发送查询，并封装结果集
        List result = query.getResultList();

        for (Object obj : result) {
            System.out.println(obj);
        }

        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();
    }


    /**
     * 统计客户数量
     *      sql: SELECT COUNT(cust_id) FROM cst_customer
     *      jpql: select count(custId) from Customer
     */
    @Test
    public void testCount() {
        //1、获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、查询全部
        String jpql = "select count(custId) from Customer";
        Query query = entityManager.createQuery(jpql);//创建query查询对象，query对象才是执行jpql的对象

        Object result = query.getSingleResult();
        System.out.println(result);

        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();

    }


    /**
     * 分页查询
     *      sql: SELECT * FROM cst_customer limit ?,?
     *      jpql: from Customer
     */
    @Test
    public void testPaged() {
        //1、获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、查询全部
        String jpql = "from Customer";
        Query query = entityManager.createQuery(jpql);//创建query查询对象，query对象才是执行jpql的对象
        //对分页参数赋值
        //起始索引
        query.setFirstResult(0);
        //每页查询的条数
        query.setMaxResults(2);

        List result = query.getResultList();
        for (Object obj : result) {
            System.out.println(obj);
        }

        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();
    }


    /**
     * 条件查询：查询客户名称以"传智播客"开头的客户
     *      sql: SELECT * FROM cst_customer WHERE cust_name LIKE ?
     *      jpql: from Customer where custName like ?1
     */
    @Test
    public void testCondition() {
        //1、获取entityManager对象
        EntityManager entityManager = JpaUtils.getEntityManager();
        //2、开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        //3、查询全部
        String jpql = "from Customer where custName like ?1";
        Query query = entityManager.createQuery(jpql);//创建query查询对象，query对象才是执行jpql的对象
        //对参数赋值 --> 占位符赋值
        query.setParameter(1, "传智播客%");

        List result = query.getResultList();
        for (Object obj : result) {
            System.out.println(obj);
        }

        //4、提交事务
        transaction.commit();
        //5、释放资源
        entityManager.close();
    }
}

