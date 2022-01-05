package com.spark.toy.service;

import com.spark.toy.domain.SubscriptionStatistics;
import com.spark.toy.repository.SubscriptionStatisticsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@Service
@Transactional
@RequiredArgsConstructor
public class SubscriptionStatisticsService {
    private final SubscriptionStatisticsRepository subscriptionStatisticsRepository;

    public SubscriptionStatistics findTodayStatistics() {
        return subscriptionStatisticsRepository.findByDate(LocalDate.now())
                .orElse(SubscriptionStatistics.builder()
                        .date(LocalDate.now())
                        .totalCount(0)
                        .failed(0)
                        .notProceeded(0)
                        .succeeded(0)
                        .build());
    }

}