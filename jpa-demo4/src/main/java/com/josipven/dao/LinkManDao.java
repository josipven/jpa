package com.josipven.dao;

import com.josipven.domain.LinkMan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author: Josipven
 * @date: 8/27/21 11:14 AM
 * @description:
 */
public interface LinkManDao extends JpaRepository<LinkMan, Long>, JpaSpecificationExecutor {
}
