package com.josipven.dao;

import com.josipven.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: Josipven
 * @date: 8/25/21 4:28 PM
 * @description:
 *  JpaRepository<实体类类型, 主键类型>: 用来完成基本CRUD操作
 *  paSpecificationExecutor<实体类类型>: 用于复杂查询（分页等查询操作）
 */
public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
}
