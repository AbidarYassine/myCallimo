package com.rest.ai.myCallimo.config.batch.secteur;

import com.rest.ai.myCallimo.dto.SecteurDto;
import com.rest.ai.myCallimo.entities.Secteur;
import com.rest.ai.myCallimo.services.facade.SecteurService;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SecteurWriter implements ItemWriter<Secteur> {
    @Autowired
    private SecteurService secteurService;

    @Override
    public void write(List<? extends Secteur> list) throws Exception {
        list.forEach(secteur -> {
            ModelMapper modelMapper = new ModelMapper();
            if (secteur != null)
                secteurService.save(modelMapper.map(secteur, SecteurDto.class));
        });
    }
}
