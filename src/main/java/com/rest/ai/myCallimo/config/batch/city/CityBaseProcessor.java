package com.rest.ai.myCallimo.config.batch.city;


import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.entities.OffreEntity;
import com.rest.ai.myCallimo.entities.base.CityBase;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import org.springframework.batch.item.ItemProcessor;

public class CityBaseProcessor implements ItemProcessor<CityBase, CityEntity> {

    @Override
    public CityEntity process(CityBase offreBase) throws Exception {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(offreBase.getOffer_city());
        return cityEntity;
    }
}
