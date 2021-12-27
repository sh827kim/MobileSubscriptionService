package com.spark.toy.repository;

import com.spark.toy.domain.Customer;
import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.SubscriptionRequest;
import com.spark.toy.domain.enums.DeviceType;
import com.spark.toy.domain.enums.SubscriptionCode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubscriptionRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionRepositoryTest.class);
    private static String TEST_PHONE_NO = "010-1234-5678";

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SubscriptionRequestRepository subscriptionRequestRepository;

    @Test
    @Order(1)
    void createTest() {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionCode(SubscriptionCode.UNOPENED);
        subscription.setPhoneNumber(TEST_PHONE_NO);
        subscription.setDeviceType(DeviceType.MOBILE);

        log.info("before create : {}", subscription);

        Subscription subscription1 = subscriptionRepository.save(subscription);

        log.info("after create : {}", subscription1);
        Assertions.assertEquals(subscription1.getId(), subscription.getId());
        Assertions.assertEquals(subscription1.getPhoneNumber(), "010-1234-5678");

    }
    @Test
    @Order(2)
    void readTest() {
        Subscription subscription = subscriptionRepository.findByPhoneNumber(TEST_PHONE_NO).orElseThrow(RuntimeException::new);

        log.info("read : {}", subscription);

        Assertions.assertNotNull(subscription);
    }
    @Test
    @Order(3)
    void updateTest() {
        Subscription subscription = subscriptionRepository.findByPhoneNumber(TEST_PHONE_NO).orElseThrow(RuntimeException::new);

        log.info("before update : {}", subscription);

        Subscription subscription1 = subscriptionRepository.save(subscription);

        log.info("after update : {}", subscription1);

    }
    @Test
    @Order(4)
    void deleteTest() {
        Subscription subscription = subscriptionRepository.findByPhoneNumber(TEST_PHONE_NO).orElseThrow(RuntimeException::new);
        UUID uuid = subscription.getId();
        subscriptionRepository.delete(subscription);
        Assertions.assertEquals(subscriptionRepository.findById(uuid).isEmpty(), true);
    }

    @Test
    @Order(5)
    void cascadeTest() {
        Customer customer = customerRepository.findByAccount("gobuk123").orElseThrow(RuntimeException::new);

        Subscription subscription = new Subscription();
        subscription.setSubscriptionCode(SubscriptionCode.UNOPENED);
        subscription.setCustomer(customer);
        subscription.setDeviceType(DeviceType.MOBILE);
        subscription.setPhoneNumber("010-3333-4444");
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        subscriptionRequest.setIsProceeded(false);
        subscriptionRequest.setRequestedAt(LocalDateTime.now());
        subscriptionRequest.setRequestSubscriptionCode(SubscriptionCode.OPENED);
        subscriptionRequest.setSubscription(subscription);

      //  subscription.setSubscriptionRequest(subscriptionRequest);
        subscriptionRequest = subscriptionRequestRepository.save(subscriptionRequest);

        System.out.println(subscriptionRequest);

       // subscriptionRequest = subscriptionRequestRepository.findBySubscription(subscription).orElseThrow(RuntimeException::new);
       // Subscription subscription1 = subscriptionRepository.findByPhoneNumber("010-2222-3333").orElseThrow(RuntimeException::new);

      //  System.out.println(subscription1);
    }
}