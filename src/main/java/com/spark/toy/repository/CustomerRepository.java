package com.spark.toy.repository;

import com.spark.toy.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long>, RevisionRepository<Customer, Long, Long> {
    List<Customer> findByName(String name);

    Optional<Customer> findByAccount(String account);

    void deleteByAccount(String account);


}
