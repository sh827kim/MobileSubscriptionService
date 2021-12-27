package com.spark.toy.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job job;


    @Scheduled(cron = "0 */1 * * * *")
    public void executeSubscriptionJob() {
        log.info("executeSubscriptionJob() called");
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addDate("date", new Date())
                            .toJobParameters();
            jobLauncher.run(job, jobParameters);

        } catch (Exception e) {
            log.error("job running failed cause : {}", e.getMessage());
        }
    }
}
