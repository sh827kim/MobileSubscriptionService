package com.spark.toy.service;

import com.spark.toy.dto.CustomerDto;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerServiceTest {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    private CustomerService customerService;

    @Test
    @Order(1)
    void getCustomersWithPageNumberTest() {
        int pageNo = 0;
        int pageSize = 5;
        List<CustomerDto> customerDtos = customerService.getCustomersWithPageNumber(pageNo, pageSize);

        int size = customerDtos.size();
        customerDtos.forEach(dto -> log.info(dto.toString()));
        log.info("size : {}", size);

        Assertions.assertEquals(5, size);

        pageNo = 1;
        List<CustomerDto> customerDtos2 = customerService.getCustomersWithPageNumber(pageNo, pageSize);

        Assertions.assertNotEquals(size, customerDtos2.size());
    }


    @Test
    @Order(2)
    void getCustomerByAccountTest() {
        CustomerDto customerDto = customerService.getCustomerByAccount("fire123");
        Assertions.assertEquals("파이리", customerDto.getName());
    }

    @Test
    @Order(3)
    void createCustomerTest() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName("피카츄");
        customerDto.setPassword("12345");
        customerDto.setBirth("2012-07-21");
        customerDto.setAccount("voltage112");
        customerDto.setPhoneNumber("010-1111-9999");

        CustomerDto customerDto1 = customerService.createCustomer(customerDto);

        Assertions.assertEquals(customerDto.getName(), customerDto1.getName());
    }

    @Test
    @Order(4)
    void updateCustomerTest() {
        CustomerDto customerDto = customerService.getCustomerByAccount("voltage112");
        String oldPassword = customerDto.getPassword();

        customerDto.setPassword("34567");
        CustomerDto customerDto1 = customerService.updateCustomer(customerDto);

        Assertions.assertNotEquals(oldPassword, customerDto1.getPassword());
    }

    @Test
    @Order(5)
    void deleteCustomerTest() {
        customerService.deleteCustomerByAccount("voltage112");
        CustomerDto customerDto = customerService.getCustomerByAccount("voltage112");
        Assertions.assertNull(customerDto);
    }


}