package com.spark.toy.repository;

import com.spark.toy.domain.Customer;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRepositoryTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerRepositoryTest.class);
    private static String TEST_USER_NAME = "나왕눈";

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Order(1)
    void createTest() {
        Customer customer = new Customer();
        customer.setName(TEST_USER_NAME);
        customer.setPhoneNumber("010-3456-7890");
        customer.setAccount("wangnoon123");

        customerRepository.save(customer);

    }
    @Test
    @Order(2)
    void readTest() {

    }

    @Test
    @Order(3)
    void updateTest() {
        Customer customer = customerRepository.findByAccount("wangnoon123").orElseThrow(RuntimeException::new);
        customer.setBirth(LocalDate.of(1994, 3, 27));

        customerRepository.save(customer);
    }

    @Test
    @Order(4)
    void deleteTest() {

    }

    @Test
    @Order(5)
    void revisionTest() {
    }
}