package com.rest.ai.myCallimo.config.batch.city;


import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.services.facade.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class CityBaseWriter implements ItemWriter<CityEntity> {


    @Autowired
    private CityService cityService;

    @Override
    public void write(List<? extends CityEntity> list) throws Exception {
        list.stream().forEach(cityEntity -> {
            log.info("Enregistrement en base de l'objet {}", cityEntity);
            cityService.save(cityEntity);
        });
    }
}
