package com.spark.toy.repository;

import com.spark.toy.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, RevisionRepository<Employee, Long, Long> {
    Optional<Employee> findByAccount(String account);

    Optional<Employee> findByPhoneNumber(String phoneNumber);
}
