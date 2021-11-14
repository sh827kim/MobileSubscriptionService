package com.spark.toy.controller;

import com.spark.toy.dto.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
        Assertions.assertEquals(5, body.size());
        body.forEach(System.out::println);

        System.out.println(">>>>>> page 1 <<<<<<");
        pageNo = 1;
        url = baseUrl + "/all?pageNo="+ pageNo + "&pageSize=" + pageSize;
        ArrayList<Object> body2 = restTemplate.getForEntity(url, ArrayList.class)
                .getBody();
        body2.forEach(System.out::println);
    }


}