package com.rest.ai.myCallimo.config.batch.annonceur;

import com.rest.ai.myCallimo.entities.base.AnnonceurBase;
import com.rest.ai.myCallimo.entities.base.CityBase;
import com.rest.ai.myCallimo.shared.Utils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RESTAnnonceurReader implements ItemReader<AnnonceurBase> {

    private final String apiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    Utils utils;

    public RESTAnnonceurReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    private int nextStudentIndex;
    private List<AnnonceurBase> annonceurBasetData;

    //    return one AnnonceurBase
    @Override
    public AnnonceurBase read() throws Exception {
        if (studentDataIsNotInitialized()) {
            annonceurBasetData = fetchStudentDataFromAPI();
        }
        AnnonceurBase nextAnnonceur = null;

        if (nextStudentIndex < annonceurBasetData.size()) {
            nextAnnonceur = annonceurBasetData.get(nextStudentIndex);
            nextStudentIndex++;
        } else {
            nextStudentIndex = 0;
            annonceurBasetData = null;
        }

        return nextAnnonceur;
    }

    private boolean studentDataIsNotInitialized() {
        return this.annonceurBasetData == null;
    }

    private List<AnnonceurBase> fetchStudentDataFromAPI() {
        ResponseEntity<AnnonceurBase[]> response = restTemplate.getForEntity(apiUrl,
                AnnonceurBase[].class
        );
        AnnonceurBase[] annonceurBasetData = response.getBody();
        System.out.println("lenght response " + annonceurBasetData.length);
        return Arrays.stream(annonceurBasetData).filter(x -> x.getOffer_telephone() != null).filter(utils.distinctByKey(AnnonceurBase::getOffer_telephone)).collect(Collectors.toList());

    }
}
