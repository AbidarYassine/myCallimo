package com.rest.ai.myCallimo.config.batch.annonceur;

import com.rest.ai.myCallimo.entities.base.AnnonceurBase;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RESTAnnonceurReader implements ItemReader<AnnonceurBase> {

    private final String apiUrl;
    private final RestTemplate restTemplate;

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
        AnnonceurBase nextStudent = null;

        if (nextStudentIndex < annonceurBasetData.size()) {
            nextStudent = annonceurBasetData.get(nextStudentIndex);
            nextStudentIndex++;
        } else {
            nextStudentIndex = 0;
            annonceurBasetData = null;
        }

        return nextStudent;
    }

    private boolean studentDataIsNotInitialized() {
        return this.annonceurBasetData == null;
    }

    private List<AnnonceurBase> fetchStudentDataFromAPI() {
        ResponseEntity<AnnonceurBase[]> response = restTemplate.getForEntity(apiUrl,
                AnnonceurBase[].class
        );
        AnnonceurBase[] annonceurBasetData = response.getBody();
        return Arrays.asList(annonceurBasetData);
    }
}
