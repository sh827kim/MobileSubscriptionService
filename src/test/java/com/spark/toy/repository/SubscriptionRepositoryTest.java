package com.spark.toy.repository;

import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.enums.DeviceType;
import com.spark.toy.domain.enums.SubscriptionCode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SubscriptionRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionRepositoryTest.class);
    private static String TEST_PHONE_NO = "010-1234-5678";
    private static String TEST_USIM_NO = "123asd2131";

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Test
    @Order(1)
    void createTest() {
        Subscription subscription = new Subscription();
        subscription.setSubscriptionCode(SubscriptionCode.UNOPENED);
        subscription.setPhoneNumber(TEST_PHONE_NO);
        subscription.setUsimNumber(TEST_USIM_NO);
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
        Assertions.assertEquals(subscription.getUsimNumber(), TEST_USIM_NO);
    }
    @Test
    @Order(3)
    void updateTest() {
        Subscription subscription = subscriptionRepository.findByPhoneNumber(TEST_PHONE_NO).orElseThrow(RuntimeException::new);

        log.info("before update : {}", subscription);

        subscription.setUsimNumber("321453asfe");
        Subscription subscription1 = subscriptionRepository.save(subscription);

        log.info("after update : {}", subscription1);

        Assertions.assertNotEquals(subscription1.getUsimNumber(), TEST_USIM_NO);
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
    void relationTest() {

    }
}