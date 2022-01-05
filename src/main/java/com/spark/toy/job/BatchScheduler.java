package com.spark.toy.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final ApplicationContext ctx;


    Job subscriptionJob() {
        return (Job) ctx.getBean("subscriptionJob");
    }


    Job subscriptionStatisticsJob() {
        return (Job) ctx.getBean("subscriptionStatisticsJob");
    }


    @Scheduled(cron = "0 */1 * * * *")
    public void executeSubscriptionJob() {


        log.info("executeSubscriptionJob() called");
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addDate("date", new Date())
                            .toJobParameters();
            jobLauncher.run(subscriptionJob(), jobParameters);

        } catch (Exception e) {
            log.error("job running failed cause : {}", e.getMessage());
        }
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void executeSubscriptionStatisticsJob() {
        log.info("executeSubscriptionStatisticsJob() called");

        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addString("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .addString("executedAt", LocalDateTime.now().toString())
                            .toJobParameters();
            jobLauncher.run(subscriptionStatisticsJob(), jobParameters);
        } catch (Exception e) {
            log.error("job running failed cause : {}", e.getMessage());
        }
    }

}
