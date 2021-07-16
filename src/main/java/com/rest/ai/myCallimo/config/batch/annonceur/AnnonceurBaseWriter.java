package com.rest.ai.myCallimo.config.batch.annonceur;

import com.rest.ai.myCallimo.entities.AnnonceurEntity;
import com.rest.ai.myCallimo.entities.base.AnnonceurBase;
import com.rest.ai.myCallimo.services.facade.AnnonceurService;
import lombok.extern.slf4j.Slf4j;
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
            log.info("Enregistrement en base de l'objet {}", annonceurBase);
            annonceurService.save(annonceurBase);
        });
    }
}