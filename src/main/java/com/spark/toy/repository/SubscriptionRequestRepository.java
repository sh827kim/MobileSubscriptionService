package com.spark.toy.repository;

import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.SubscriptionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRequestRepository extends JpaRepository<SubscriptionRequest, Long> {
    Optional<SubscriptionRequest> findBySubscription(Subscription subscription);

    List<SubscriptionRequest> findByIsProceeded(Boolean isProceeded);
    Page<SubscriptionRequest> findByIsProceededFalseOrIsProceeded(Pageable pageable, Boolean isProceeded);
}
