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
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SubscriptionBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final SubscriptionRequestRepository subscriptionRequestRepository;
    private final SubscriptionStatisticsRepository subscriptionStatisticsRepository;


    @Bean
    public Job subscriptionJob() throws Exception {
        return jobBuilderFactory.get("subscriptionJob")
                .incrementer(new RunIdIncrementer())
                .start(subscriptionStep())
            //    .next(statisticsStep())
                .build();
    }

    @Bean
    public Step testStep() {
        return stepBuilderFactory.get("testStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("hello spring batch");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step subscriptionStep() throws Exception {
        return stepBuilderFactory.get("subscriptionStep")
                .<SubscriptionRequest, SubscriptionRequest> chunk(10)
                .reader(subscriptionRequestJpaPagingItemReader())
                .processor(subscriptionRequestItemProcessor())
                .writer(subscriptionRequestItemWriter())
                .build();
    }

    @Bean Step statisticsStep() {
        return null;
    }


    private JpaPagingItemReader<SubscriptionStatistics> subscriptionStatisticsJpaPagingItemReader() throws Exception {
        JpaPagingItemReader<SubscriptionStatistics> itemReader = new JpaPagingItemReaderBuilder<SubscriptionStatistics>()
                .name("subsStatisticsItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select s from SubscriptionStatistics s where s.date=now()")
                .pageSize(10)
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

    private ItemWriter<? super SubscriptionRequest> subscriptionRequestItemWriter() {
        return subscriptionRequests -> {
            subscriptionRequestRepository.saveAll(subscriptionRequests);
        };
    }

}
