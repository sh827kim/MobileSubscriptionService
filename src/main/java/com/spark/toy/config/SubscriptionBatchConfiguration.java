package com.spark.toy.config;

import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.SubscriptionRequest;
import com.spark.toy.domain.SubscriptionStatistics;
import com.spark.toy.domain.enums.SubscriptionCode;
import com.spark.toy.repository.SubscriptionRequestRepository;
import com.spark.toy.repository.SubscriptionStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SubscriptionBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;
    private final EntityManagerFactory entityManagerFactory;
    private final SubscriptionRequestRepository subscriptionRequestRepository;
    private final SubscriptionStatisticsRepository subscriptionStatisticsRepository;
    private static final Integer CHUNK_SIZE = 10;


    @Bean
    public Job subscriptionStatisticsJob() throws Exception {
        return jobBuilderFactory.get("subscriptionStatisticsJob")
                .incrementer(new RunIdIncrementer())
                .start(statisticsStep(null))
                .build();
    }

    @Bean
    public Job subscriptionJob() throws Exception {
        return jobBuilderFactory.get("subscriptionJob")
                .incrementer(new RunIdIncrementer())
                .start(subscriptionStep())
                .build();
    }

    @Bean
    public Step subscriptionStep() throws Exception {
        return stepBuilderFactory.get("subscriptionStep")
                .<SubscriptionRequest, SubscriptionRequest> chunk(CHUNK_SIZE)
                .reader(subscriptionRequestJpaPagingItemReader())
                .processor(subscriptionRequestItemProcessor())
                .writer(subscriptionRequestItemWriter())
                .build();
    }

    @Bean
    @JobScope
    public Step statisticsStep(@Value("#{jobParameters[date]}") String date) throws Exception {
        return stepBuilderFactory.get("subscriptionStatisticsStep")
                .<SubscriptionStatistics, SubscriptionStatistics> chunk(CHUNK_SIZE)
                .reader(subscriptionStatisticsJdbcPagingItemReader(date))
                .writer(subscriptionStatisticsItemWriter())
                .build();
    }

    private JdbcPagingItemReader<SubscriptionStatistics> subscriptionStatisticsJdbcPagingItemReader(String date) throws Exception {
        Map<String,Object> params = Map.of("date", date + " 00:00:00");

        JdbcPagingItemReader<SubscriptionStatistics> itemReader = new JdbcPagingItemReaderBuilder<SubscriptionStatistics>()
                .dataSource(this.dataSource)
                .rowMapper((resultSet, i) -> SubscriptionStatistics.builder()
                        .date(LocalDate.parse(date , DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .totalCount(resultSet.getInt(1))
                        .notProceeded(resultSet.getInt(2))
                        .succeeded(resultSet.getInt(3))
                        .failed(resultSet.getInt(4))
                        .build())
                .pageSize(10)
                .name("subscriptionStatisticsItemReader")
                .selectClause("count(1) as totalCount, " +
                        "count(case when is_proceeded=false then 1 end) as notProceeded, " +
                        "count(case when is_proceeded=true and subscription_id is not null then 1 end) as succeeded, " +
                        "count(case when is_proceeded=true and subscription_id is null then 1 end) as failed")
                .fromClause("subscription_request")
                .whereClause("created_at >= :date")
                .parameterValues(params)
                .sortKeys(Map.of("totalCount", Order.ASCENDING))
                .build();
        itemReader.afterPropertiesSet();

        return itemReader;
    }


    private JpaPagingItemReader<SubscriptionRequest> subscriptionRequestJpaPagingItemReader() throws Exception {
        JpaPagingItemReader<SubscriptionRequest> itemReader = new JpaPagingItemReaderBuilder<SubscriptionRequest>()
                .name("subsRequestItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select s from SubscriptionRequest s where s.isProceeded=false")
                .pageSize(10)
                .build();
        itemReader.afterPropertiesSet();

        return itemReader;
    }



    private ItemProcessor<? super SubscriptionRequest, ? extends SubscriptionRequest> subscriptionRequestItemProcessor() {
        return subs -> {
            if(subs.getSubscription()!=null) {
                Subscription subscription = subs.getSubscription();
                subscription.setSubscriptionCode(subs.getRequestSubscriptionCode());
                if(subscription.getSubscriptionCode().equals(SubscriptionCode.OPENED)) {
                    subscription.setSubscribedAt(LocalDateTime.now());
                } else if(subscription.getSubscriptionCode().equals(SubscriptionCode.EXPIRED)) {
                    subscription.setExpiredAt(LocalDateTime.now());
                }
                subs.setSubscription(subscription);
                subs.setIsProceeded(true);
                subs.setProceededAt(LocalDateTime.now());
                subs.setUpdatedAt(LocalDateTime.now());
                return subs;
            }

          return null;
        };
    }

    private ItemWriter<? super SubscriptionStatistics> subscriptionStatisticsItemWriter() {
        return new JpaItemWriterBuilder<SubscriptionStatistics>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    private ItemWriter<? super SubscriptionRequest> subscriptionRequestItemWriter() {
        return subscriptionRequestRepository::saveAll;
    }

}
