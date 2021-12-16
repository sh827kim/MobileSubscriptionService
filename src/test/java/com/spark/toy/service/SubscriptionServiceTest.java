package com.spark.toy.service;

import com.spark.toy.domain.Customer;
import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.enums.DeviceType;
import com.spark.toy.domain.enums.SubscriptionCode;
import com.spark.toy.dto.CustomerDto;
import com.spark.toy.dto.SubscriptionDto;
import com.spark.toy.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubscriptionServiceTest {
    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @Order(1)
    @DisplayName("1. createSubscription Test")
    @Transactional
    void createSubscriptionTest() {
        Customer customer = customerRepository.findByAccount("gobuk123").orElseThrow(RuntimeException::new);

        System.out.println(customer);
        SubscriptionDto subscriptionDto = SubscriptionDto.builder()
                .subscriptionCode(SubscriptionCode.UNOPENED)
                .deviceType(DeviceType.MOBILE)
                .phoneNumber("010-1234-4321")
               // .customerDto(CustomerDto.toDto(customer))
                .usimNumber("21sfew11344")
                .subscriptionRequestDto(null)
                .build();

        SubscriptionDto result = subscriptionService.createSubscription(subscriptionDto);

        System.out.println("RESULT ======> " + result);

        assertEquals("21sfew11344", result.getUsimNumber());
    }

    @Test
    @Order(2)
    @DisplayName("2. find by is Proceeded Test")
    void findByIsProceededTest() {
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptionsWithIsProceeded(false);

        assertEquals(1, subscriptionDtos.size());
        subscriptionDtos.forEach(dto -> System.out.println(dto.toString()));
    }

}
