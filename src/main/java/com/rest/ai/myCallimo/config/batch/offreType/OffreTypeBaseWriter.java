package com.rest.ai.myCallimo.config.batch.offreType;


import com.rest.ai.myCallimo.dto.OffreTypeDto;
import com.rest.ai.myCallimo.entities.OffreTypeEntity;
import com.rest.ai.myCallimo.services.facade.OffreTypeService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
            ModelMapper modelMapper = new ModelMapper();
            if (offreTypeEntity != null)
                offreTypeService.save(modelMapper.map(offreTypeEntity, OffreTypeDto.class));

        });
    }
}
