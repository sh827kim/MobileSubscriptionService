package com.spark.toy.controller;

import com.spark.toy.dto.CustomerDto;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerControllerTest {

    String baseUrl = "http://localhost:8081/api/customers";

    TestRestTemplate restTemplate = new TestRestTemplate("admin@mail.com", "!new1234");

    @Order(1)
    @Test
    void getAllWithPageNumberTest() {
        int pageNo = 0;
        int pageSize = 5;
        String url = baseUrl + "/all?pageNo="+ pageNo + "&pageSize=" + pageSize;
        System.out.println(">>>>>> page 0 <<<<<<");

        ArrayList<Object> body = restTemplate.getForEntity(url, ArrayList.class)
                .getBody();
        assertEquals(5, body.size());
        body.forEach(System.out::println);

        System.out.println(">>>>>> page 1 <<<<<<");
        pageNo = 1;
        url = baseUrl + "/all?pageNo="+ pageNo + "&pageSize=" + pageSize;
        ArrayList<Object> body2 = restTemplate.getForEntity(url, ArrayList.class)
                .getBody();
        body2.forEach(System.out::println);
    }

    @Order(2)
    @Test
    void getCustomerByAccountTest() {
        String account = "gobuk123";
        String url = baseUrl + "?account=" + account;
        CustomerDto body = restTemplate.getForEntity(url, CustomerDto.class).getBody();

        System.out.println(body.toString());
        assertEquals("꼬부기", body.getName());
    }


    @Order(3)
    @Test
    void createCustomerTest() {
        CustomerDto customerDto = getCustomer();
        CustomerDto result = restTemplate.postForEntity(baseUrl, customerDto, CustomerDto.class).getBody();

        System.out.println(result);
        assertEquals("갸라도스", result.getName());
    }

    @Order(4)
    @Test
    void updateCustomerTest() {
        CustomerDto customerDto = getCustomer();
        String oldPhoneNumber= customerDto.getPhoneNumber();
        customerDto.setPhoneNumber("010-7989-4982");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CustomerDto> httpEntity = new HttpEntity<>(customerDto, headers);


        CustomerDto result = restTemplate.exchange(baseUrl, HttpMethod.PUT, httpEntity, CustomerDto.class).getBody();

        System.out.println(result);
        assertNotEquals(oldPhoneNumber, result.getPhoneNumber());
    }

    @Order(5)
    @Test
    void deleteCustomerByAccountTest() {
        String account = "wdragon001";
        String url = baseUrl + "?account=" + account;

        restTemplate.delete(url);

        CustomerDto body = restTemplate.getForEntity(url, CustomerDto.class).getBody();

        assertNull(body);
    }

    CustomerDto getCustomer() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setBirth("1992-06-30");
        customerDto.setPhoneNumber("010-9873-4325");
        customerDto.setAccount("wdragon001");
        customerDto.setName("갸라도스");
        customerDto.setPassword("!new12345");

        return customerDto;
    }
}