package com.josipven.test;

import com.josipven.dao.CustomerDao;
import com.josipven.domain.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author: Josipven
 * @date: 8/26/21 5:58 PM
 * @description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpecTest {

    @Autowired
    private CustomerDao customerDao;


    /**
     * 根据条件查询单个对象
     */
    @Test
    public void testSpec1() {
        /**
         * 匿名内部类
         * 1、实现Specification接口（提供泛型，查询的对象类型）
         * 2、实现toPredicate方法（构造查询条件）
         * 3、需要借助方法参数中的两个参数（
         *      root：获取需要查询的对象属性
         *      CriteriaBuilder：构造查询条件，内部封装了很多的查询条件（模糊匹配，精准匹配）
         *      ）
         *
         *      查询条件：
         *          1、查询方式
         *              builder对象
         *          2、比较的属性值
         *              root对象
         */
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                //1、获取比较的属性
                Path<Object> custName = root.get("custName");
                //2、构造查询条件 select * from cst_customer where cust_name = `传智播客`
                /**
                 * 第一个参数：需要比较的属性（path对象）
                 * 第二个参数：当前需要计较的取值
                 */
                Predicate predicate = builder.equal(custName, "传智播客");//精准匹配
                return predicate;
            }
        };
        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }


    /**
     * 根据客户名和客户行业查询客户
     */
    @Test
    public void testSpec2() {
        /**
         * root：获取属性
         *      客户名
         *      客户行业
         * builder：构造查询
         *      构造客户名的精确查询
         *      构造客户行业的精确查询
         *      将以上两个查询联系起来
         */
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                Path<Object> custName = root.get("custName");//客户名
                Path<Object> custIndustry = root.get("custIndustry");//客户行业

                //构造查询
                Predicate p1 = builder.equal(custName, "josipven");//客户名精确匹配
                Predicate p2 = builder.equal(custIndustry, "IT");//客户行业精确匹配
                //将多个查询条件组合在一起
                Predicate and = builder.and(p1, p2);//and：以与的形式拼接在一起
                return and;
            }
        };

        Customer customer = customerDao.findOne(spec);
        System.out.println(customer);
    }


    /**
     * 根据客户名称模糊匹配，返回客户列表
     * 得到path对象，根据path指定比较的参数类型，再去进行比较
     */
    
    @Test
    public void testSpec3() {
        //
        Specification<Customer> spec = new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
//                Path<String> custName = root.get("custName");
//                builder.like(custName, "josipven");
                Path<Object> custName = root.get("custName");
                Predicate like = builder.like(custName.as(String.class), "josipven%");
                return like;
            }
        };
//        List<Customer> list = customerDao.findAll(spec);
//        for (Customer customer : list) {
//            System.out.println(customer);
//        }
        //添加排序
        /**
         * 创建排序对象，需要调用构造方法实例化sort对象
         * 第一个参数：排序的顺序
         * Sort.Direction.ASC 倒序
         * Sort.Direction.DESC 升序
         * 第二个参数：排序的属性名称
         */

        Sort sort = new Sort(Sort.Direction.DESC, "custId");
        List<Customer> list = customerDao.findAll(spec, sort);
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }


    /**
     * 分页查询
     *
     *      Specification：查询条件
     *      Pageable：分页参数
     *          分页参数：每页显示的条数
     *          findAll(Pageable)：没有条件的分页
     *      返回：Page（springDataJpa为我们封装好的pageBean对象，数据列表，总条数）
     */
    @Test
    public void testSpec4() {

        Specification spec = null;
        /**
         * PageRequest对象是Pageable接口的实现类
         * 创建PageRequest的过程中，需要调用他的构造方法传入两个参数
         *      第一个参数：当前查询的页数（从0开始）
         *      第二个参数：每页查询的条数
         */
        Pageable pageable = new PageRequest(0, 2);
        //分页查询
        Page page = customerDao.findAll(spec, pageable);
        System.out.println(page.getContent());//得到的数据列表
        System.out.println(page.getTotalElements());//得到的总条数
        System.out.println(page.getTotalPages());//得到的总页数
    }




}

