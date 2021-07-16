package com.rest.ai.myCallimo.config.batch.offre;


import com.rest.ai.myCallimo.entities.base.OffreBase;
import org.springframework.batch.item.ItemReader;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class OffreBaseReader implements ItemReader<OffreBase> {
    private final String apiUrl;
    private final RestTemplate restTemplate;

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
        OffreBase[] annonceurBasetData = response.getBody();
        return Arrays.asList(annonceurBasetData);
    }
}
