package com.rest.ai.myCallimo.config.batch.annonceur;

import com.rest.ai.myCallimo.dto.AnnonceurDto;
import com.rest.ai.myCallimo.entities.AnnonceurEntity;
import com.rest.ai.myCallimo.entities.base.AnnonceurBase;
import com.rest.ai.myCallimo.services.facade.AnnonceurService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class AnnonceurBaseWriter implements ItemWriter<AnnonceurEntity> {

    @Autowired
    private AnnonceurService annonceurService;

    @Override
    public void write(List<? extends AnnonceurEntity> annonceurBases) {
        annonceurBases.stream().forEach(annonceurBase -> {
            ModelMapper modelMapper = new ModelMapper();
            annonceurService.save(modelMapper.map(annonceurBase, AnnonceurDto.class));
        });
    }
}