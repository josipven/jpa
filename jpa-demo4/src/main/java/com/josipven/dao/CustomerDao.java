package com.josipven.dao;

import com.josipven.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CustomerDao extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {


}
