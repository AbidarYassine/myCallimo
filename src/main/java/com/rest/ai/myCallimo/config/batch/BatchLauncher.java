package com.rest.ai.myCallimo.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchLauncher {


    private final JobLauncher jobLauncher;


    private final Job job;

    @Autowired
    public BatchLauncher(JobLauncher jobLauncher, Job job) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        this.jobLauncher = jobLauncher;
        this.job = job;
//        run();
    }

    public BatchStatus run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
//        if (jobRepository.getLastJobExecution("Job", parameters) == null || !jobRepository.getLastJobExecution("Job", parameters).getStatus().name().equalsIgnoreCase("COMPLETED")) {
//            JobExecution jobExecution = jobLauncher.run(job, parameters);
//            BatchStatus batchStatus = jobExecution.getStatus();
//            log.info("le status du batch du job " + batchStatus.name());
//            return batchStatus;
//        }
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        return jobExecution.getStatus();
    }

//    @SneakyThrows
//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        if (alreadySetup) {
//            alreadySetup = false;
//            return;
//        } else {
//            this.run();
//            alreadySetup = true;
//        }
//    }
}
