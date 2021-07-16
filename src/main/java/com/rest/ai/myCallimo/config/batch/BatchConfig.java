package com.rest.ai.myCallimo.config.batch;


import com.rest.ai.myCallimo.config.batch.annonceur.AnnonceurBaseProcessor;
import com.rest.ai.myCallimo.config.batch.annonceur.AnnonceurBaseWriter;
import com.rest.ai.myCallimo.config.batch.annonceur.RESTAnnonceurReader;
import com.rest.ai.myCallimo.config.batch.category.CategoryBaseProcessor;
import com.rest.ai.myCallimo.config.batch.category.CategoryBaseReader;
import com.rest.ai.myCallimo.config.batch.category.CategoryBaseWriter;
import com.rest.ai.myCallimo.config.batch.city.CityBaseProcessor;
import com.rest.ai.myCallimo.config.batch.city.CityBaseReader;
import com.rest.ai.myCallimo.config.batch.city.CityBaseWriter;
import com.rest.ai.myCallimo.config.batch.offre.OffreBaseProcessor;
import com.rest.ai.myCallimo.config.batch.offre.OffreBaseReader;
import com.rest.ai.myCallimo.config.batch.offre.OffreBaseWriter;
import com.rest.ai.myCallimo.config.batch.offreType.OffreTypeBaseProcessor;
import com.rest.ai.myCallimo.config.batch.offreType.OffreTypeBaseReader;
import com.rest.ai.myCallimo.config.batch.offreType.OffreTypeBaseWriter;
import com.rest.ai.myCallimo.entities.*;
import com.rest.ai.myCallimo.entities.base.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    private static final String JOB_NAME = "annonceBaseJob";
    private static final String STEP_NAME_1 = "processingStep1";
    private static final String STEP_NAME_2 = "processingStep2";
    private static final String STEP_NAME_3 = "processingStep3";
    private static final String STEP_NAME_4 = "processingStep4";
    private static final String READER_NAME = "annonceBaseJobItemReader";


    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //    Step 1
    @Bean
    public Step annonceurBaseStep() {
        return stepBuilderFactory.get(STEP_NAME_1)
                .<AnnonceurBase, AnnonceurEntity>chunk(5)
                .reader(itemReaderAnnonce(restTemplate()))
                .processor(annoBaseItemProcessor())
                .writer(annonceItemWriter())
                .build();
    }

    @Bean
    public Step offreBaseStep() {
        return stepBuilderFactory.get(STEP_NAME_2)
                .<OffreBase, OffreEntity>chunk(5)
                .reader(itemReaderOffre(restTemplate()))
                .processor(offreBaseItemProcessor())
                .writer(offreItemWriter())
                .build();
    }

    @Bean
    public Step cityBaseStep() {
        return stepBuilderFactory.get(STEP_NAME_3)
                .<CityBase, CityEntity>chunk(5)
                .reader(itemReaderCity(restTemplate()))
                .processor(cityBaseItemProcessor())
                .writer(cityItemWriter())
                .build();
    }

    //    offre Type step
    @Bean
    public Step offreTypeBaseStep() {
        return stepBuilderFactory.get(STEP_NAME_3)
                .<OffreTypeBase, OffreTypeEntity>chunk(5)
                .reader(itemReaderOffreType(restTemplate()))
                .processor(offreTypeItemProcessor())
                .writer(offreTypeItemWriter())
                .build();
    }


    @Bean
    public Step categoryBaseStep() {
        return stepBuilderFactory.get(STEP_NAME_4)
                .<CategoryBase, CategoryEntity>chunk(5)
                .reader(itemReaderCategory(restTemplate()))
                .processor(catergoryBaseItemProcessor())
                .writer(categoryItemWriter())
                .build();
    }

    //    defin job execute step
    @Bean
    public Job listStudentsJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(annonceurBaseStep())
                .next(offreTypeBaseStep())
                .next(categoryBaseStep())
                .next(cityBaseStep())
                .build();
    }


//    annonce

    @Bean
    public ItemReader<AnnonceurBase> itemReaderAnnonce(RestTemplate restTemplate) {
        return new RESTAnnonceurReader("https://myspace.espaceo.net/api/get-pap-offers", restTemplate);
    }

    @Bean
    public ItemProcessor<AnnonceurBase, AnnonceurEntity> annoBaseItemProcessor() {
        return new AnnonceurBaseProcessor();
    }

    @Bean
    public ItemWriter<AnnonceurEntity> annonceItemWriter() {
        return new AnnonceurBaseWriter();
    }

    // offre
    @Bean
    public ItemProcessor<OffreBase, OffreEntity> offreBaseItemProcessor() {
        return new OffreBaseProcessor();
    }

    @Bean
    public ItemReader<OffreBase> itemReaderOffre(RestTemplate restTemplate) {
        return new OffreBaseReader("https://myspace.espaceo.net/api/get-pap-offers", restTemplate);
    }

    @Bean
    public ItemWriter<OffreEntity> offreItemWriter() {
        return new OffreBaseWriter();
    }

    //    category
    @Bean
    public ItemProcessor<CategoryBase, CategoryEntity> catergoryBaseItemProcessor() {
        return new CategoryBaseProcessor();
    }

    @Bean
    public ItemReader<CategoryBase> itemReaderCategory(RestTemplate restTemplate) {
        return new CategoryBaseReader("https://myspace.espaceo.net/api/get-pap-offers", restTemplate);
    }

    @Bean
    public ItemWriter<CategoryEntity> categoryItemWriter() {
        return new CategoryBaseWriter();
    }

    //    city
    @Bean
    public ItemProcessor<CityBase, CityEntity> cityBaseItemProcessor() {
        return new CityBaseProcessor();
    }

    @Bean
    public ItemReader<CityBase> itemReaderCity(RestTemplate restTemplate) {
        return new CityBaseReader("https://myspace.espaceo.net/api/get-pap-offers", restTemplate);
    }

    @Bean
    public ItemWriter<CityEntity> cityItemWriter() {
        return new CityBaseWriter();
    }

    //    Offre Type
    @Bean
    public ItemProcessor<OffreTypeBase, OffreTypeEntity> offreTypeItemProcessor() {
        return new OffreTypeBaseProcessor();
    }

    @Bean
    public ItemReader<OffreTypeBase> itemReaderOffreType(RestTemplate restTemplate) {
        return new OffreTypeBaseReader("https://myspace.espaceo.net/api/get-pap-offers", restTemplate);
    }

    @Bean
    public ItemWriter<OffreTypeEntity> offreTypeItemWriter() {
        return new OffreTypeBaseWriter();
    }


}
