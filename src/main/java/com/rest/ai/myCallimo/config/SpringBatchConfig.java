package com.rest.ai.myCallimo.config;


import com.rest.ai.myCallimo.entities.OffreEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;

import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


public class SpringBatchConfig {

//    @Bean
//    public Job job(JobBuilderFactory jobBuilderFactory,
//                   StepBuilderFactory stepBuilderFactory,
//                   ItemReader<OffreEntity> itemReader,
//                   ItemProcessor<OfferEntity, OfferEntity> itemProcessor,
//                   ItemWriter<OfferEntity> itemWriter
//    ) {
//
//        Step step = stepBuilderFactory.get("ETL-file-load")
//                .<OfferEntity, OfferEntity>chunk(100)
//                .reader(itemReader)
//                .processor(itemProcessor)
//                .writer(itemWriter)
//                .build();
//
//
//        return jobBuilderFactory.get("ETL-Load")
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//    }
//    @Bean
//    public FlatFileItemReader<OfferEntity> itemReader() {
//
//        FlatFileItemReader<OfferEntity> flatFileItemReader = new FlatFileItemReader<>();
//        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
//        flatFileItemReader.setName("CSV-Reader");
//        flatFileItemReader.setLinesToSkip(1);
//        flatFileItemReader.setLineMapper(lineMapper());
//        return flatFileItemReader;
//    }
//
//    @Bean
//    public LineMapper<OfferEntity> lineMapper() {
//
//        DefaultLineMapper<OfferEntity> defaultLineMapper = new DefaultLineMapper<>();
//        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//
//        lineTokenizer.setDelimiter(",");
//        lineTokenizer.setStrict(false);
//        lineTokenizer.setNames("id", "name", "dept", "salary");
//
//        BeanWrapperFieldSetMapper<OfferEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(OfferEntity.class);
//
//        defaultLineMapper.setLineTokenizer(lineTokenizer);
//        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
//
//        return defaultLineMapper;
//    }
}
