package com.rest.ai.myCallimo.config.batch.offreType;


import com.rest.ai.myCallimo.entities.OffreTypeEntity;
import com.rest.ai.myCallimo.entities.base.OffreTypeBase;
import org.springframework.batch.item.ItemProcessor;

public class OffreTypeBaseProcessor implements ItemProcessor<OffreTypeBase, OffreTypeEntity> {

    @Override
    public OffreTypeEntity process(OffreTypeBase offreBase) throws Exception {
        OffreTypeEntity offreTypeEntity = new OffreTypeEntity();
        offreTypeEntity.setType(offreBase.getOffer_type());
        return offreTypeEntity;
    }
}
