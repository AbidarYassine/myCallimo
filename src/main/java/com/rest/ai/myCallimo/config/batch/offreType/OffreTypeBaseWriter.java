package com.rest.ai.myCallimo.config.batch.offreType;


import com.rest.ai.myCallimo.entities.OffreTypeEntity;
import com.rest.ai.myCallimo.services.facade.OffreTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
public class OffreTypeBaseWriter implements ItemWriter<OffreTypeEntity> {

    @Autowired
    private OffreTypeService offreTypeService;

    @Override
    public void write(List<? extends OffreTypeEntity> list) throws Exception {
        list.stream().forEach(offreTypeEntity -> {
            log.info("Enregistrement en base de l'objet {}", offreTypeEntity);
            offreTypeService.save(offreTypeEntity);
        });
    }
}
