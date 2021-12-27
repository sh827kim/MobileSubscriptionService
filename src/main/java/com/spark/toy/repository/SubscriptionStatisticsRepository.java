package com.spark.toy.repository;

import com.spark.toy.domain.SubscriptionStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SubscriptionStatisticsRepository extends JpaRepository<SubscriptionStatistics, Long> {
    Optional<SubscriptionStatistics> findByDate(LocalDate date);

}