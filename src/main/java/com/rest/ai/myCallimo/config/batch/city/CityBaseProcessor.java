package com.rest.ai.myCallimo.config.batch.city;


import com.rest.ai.myCallimo.entities.CityEntity;
import com.rest.ai.myCallimo.entities.base.CityBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class CityBaseProcessor implements ItemProcessor<CityBase, CityEntity> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CityEntity process(CityBase cityBase) throws Exception {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setName(cityBase.getOffer_city());
        return cityEntity;
    }
}
