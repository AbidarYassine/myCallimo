package com.rest.ai.myCallimo.config.batch.offre;


import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.services.facade.OffreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class OffreBaseWriter implements ItemWriter<OffreEntity> {

    @Autowired
    private OffreService offreService;

    @Override
    public void write(List<? extends OffreEntity> list) throws Exception {
        list.stream().forEach(offreEntity -> {
            log.info("Saving offer  data ...");
            offreService.save(offreEntity);
        });
    }
}
