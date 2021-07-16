package com.rest.ai.myCallimo.config.batch.offreType;


import com.rest.ai.myCallimo.entities.base.CityBase;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import com.rest.ai.myCallimo.entities.base.OffreTypeBase;
import com.rest.ai.myCallimo.shared.Utils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OffreTypeBaseReader implements ItemReader<OffreTypeBase> {
    private final String apiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    private Utils utils;

    public OffreTypeBaseReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    private int nextOffreTypeIndex;
    private List<OffreTypeBase> offreTypeBasetData;

    //    return
    @Override
    public OffreTypeBase read() throws Exception {
        if (studentDataIsNotInitialized()) {
            offreTypeBasetData = fetchStudentDataFromAPI();
        }
        OffreTypeBase nextOffreType = null;

        if (nextOffreTypeIndex < offreTypeBasetData.size()) {
            nextOffreType = offreTypeBasetData.get(nextOffreTypeIndex);
            nextOffreTypeIndex++;
        } else {
            nextOffreTypeIndex = 0;
            offreTypeBasetData = null;
        }
        return nextOffreType;
    }

    private boolean studentDataIsNotInitialized() {
        return this.offreTypeBasetData == null;
    }

    private List<OffreTypeBase> fetchStudentDataFromAPI() {
        ResponseEntity<OffreTypeBase[]> response = restTemplate.getForEntity(apiUrl,
                OffreTypeBase[].class
        );
        OffreTypeBase[] offreTypeBasetData = response.getBody();
        return Arrays.stream(offreTypeBasetData).filter(utils.distinctByKey(OffreTypeBase::getOffer_type)).collect(Collectors.toList());
    }
}
