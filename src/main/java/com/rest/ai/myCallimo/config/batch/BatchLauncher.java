package com.rest.ai.myCallimo.config.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchLauncher {


    private final JobLauncher jobLauncher;
    private final JobRepository jobRepository;


    private final Job job;

    @Autowired
    public BatchLauncher(JobLauncher jobLauncher, JobRepository jobRepository, Job job) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        this.jobLauncher = jobLauncher;
        this.jobRepository = jobRepository;
        this.job = job;
//        run();
    }

    public BatchStatus run() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParameters parameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
        if (jobRepository.getLastJobExecution("annonceBaseJob", parameters) == null || !jobRepository.getLastJobExecution("annonceBaseJob", parameters).getStatus().name().equalsIgnoreCase("COMPLETED")) {
            JobExecution jobExecution = jobLauncher.run(job, parameters);
            BatchStatus batchStatus = jobExecution.getStatus();
            log.info("le status du batch du job " + batchStatus.name());
            return batchStatus;
        }
        return jobRepository.getLastJobExecution("annonceBaseJob", parameters).getStatus();
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
