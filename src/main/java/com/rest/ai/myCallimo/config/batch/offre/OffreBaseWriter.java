package com.rest.ai.myCallimo.config.batch.offre;


import com.rest.ai.myCallimo.entities.OffreEntity;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class OffreBaseWriter implements ItemWriter<OffreEntity> {
    @Override
    public void write(List<? extends OffreEntity> list) throws Exception {

    }
}
