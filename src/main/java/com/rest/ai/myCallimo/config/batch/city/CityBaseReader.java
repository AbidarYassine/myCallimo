package com.rest.ai.myCallimo.config.batch.city;


import com.rest.ai.myCallimo.entities.base.CityBase;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import com.rest.ai.myCallimo.shared.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CityBaseReader implements ItemReader<CityBase> {
    private final String apiUrl;
    private final RestTemplate restTemplate;
    @Autowired
    Utils utils;

    public CityBaseReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    private int nextOffreIndex;
    private List<CityBase> cityBasetData;

    //    return
    @Override
    public CityBase read() throws Exception {
        if (studentDataIsNotInitialized()) {
            cityBasetData = fetchStudentDataFromAPI();
        }
        CityBase nextCity = null;

        if (nextOffreIndex < cityBasetData.size()) {
            nextCity = cityBasetData.get(nextOffreIndex);
            nextOffreIndex++;
        } else {
            nextOffreIndex = 0;
            cityBasetData = null;
        }

        return nextCity;
    }

    private boolean studentDataIsNotInitialized() {
        return this.cityBasetData == null;
    }

    private List<CityBase> fetchStudentDataFromAPI() {
        ResponseEntity<CityBase[]> response = restTemplate.getForEntity(apiUrl,
                CityBase[].class
        );
        CityBase[] cityBasetData = response.getBody();
        log.info("cityBasetData size " + cityBasetData.length);
            return Arrays.stream(cityBasetData).filter(x -> x.getOffer_city() != null).filter(utils.distinctByKey(CityBase::getOffer_city)).collect(Collectors.toList());
//        return Arrays.asList(cityBasetData);
    }
}
