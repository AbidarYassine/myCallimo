package com.rest.ai.myCallimo.config.batch.offre;


import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import org.springframework.batch.item.ItemProcessor;

public class OffreBaseProcessor implements ItemProcessor<OffreBase, OffreEntity> {


    //    get category by name
//    get annonceur by name or by telephone
//    get city by name
//    get offre type
    @Override
    public OffreEntity process(OffreBase offreBase) throws Exception {
        return null;
    }
}
