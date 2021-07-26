package com.rest.ai.myCallimo.config.batch.offre;


import com.rest.ai.myCallimo.entities.base.CityBase;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import com.rest.ai.myCallimo.shared.Utils;
import org.springframework.batch.item.ItemReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OffreBaseReader implements ItemReader<OffreBase> {
    private final String apiUrl;
    private final RestTemplate restTemplate;
    @Autowired
    private Utils utils;

    public OffreBaseReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    private int nextOffreIndex;
    private List<OffreBase> offreBasetData;

    //    return
    @Override
    public OffreBase read() throws Exception {
        if (studentDataIsNotInitialized()) {
            offreBasetData = fetchStudentDataFromAPI();
        }
        OffreBase nextOffre = null;

        if (nextOffreIndex < offreBasetData.size()) {
            nextOffre = offreBasetData.get(nextOffreIndex);
            nextOffreIndex++;
        } else {
            nextOffreIndex = 0;
            offreBasetData = null;
        }

        return nextOffre;
    }

    private boolean studentDataIsNotInitialized() {
        return this.offreBasetData == null;
    }

    private List<OffreBase> fetchStudentDataFromAPI() {
        ResponseEntity<OffreBase[]> response = restTemplate.getForEntity(apiUrl,
                OffreBase[].class
        );
        OffreBase[] offreBasetData = response.getBody();
        return Arrays.stream(offreBasetData).filter(x -> x.getOffer_id() != null).filter(utils.distinctByKey(OffreBase::getOffer_id)).collect(Collectors.toList());
    }
}
