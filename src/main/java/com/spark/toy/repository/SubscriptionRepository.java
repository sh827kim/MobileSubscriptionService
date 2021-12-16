package com.spark.toy.repository;

import com.spark.toy.domain.Employee;
import com.spark.toy.domain.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionRepository extends JpaRepository<Subscription, UUID>, RevisionRepository<Subscription, UUID, Long> {
    Optional<Subscription> findByPhoneNumber(String phoneNumber);
}
