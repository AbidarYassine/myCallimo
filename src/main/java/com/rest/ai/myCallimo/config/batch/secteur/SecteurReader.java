package com.rest.ai.myCallimo.config.batch.secteur;

import com.rest.ai.myCallimo.entities.base.SecteurBase;
import com.rest.ai.myCallimo.shared.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class SecteurReader implements ItemReader<SecteurBase> {

    private final String apiUrl;
    private final RestTemplate restTemplate;
    @Autowired
    private Utils utils;
    private int nextOffreIndex;
    private List<SecteurBase> secteurBases;

    @Autowired
    public SecteurReader(String apiUrl, RestTemplate restTemplate) {
        this.apiUrl = apiUrl;
        this.restTemplate = restTemplate;
    }

    @Override
    public SecteurBase read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (studentDataIsNotInitialized()) {
            secteurBases = fetchStudentDataFromAPI();
        }
        SecteurBase nextSecteur = null;

        if (nextOffreIndex < secteurBases.size()) {
            nextSecteur = secteurBases.get(nextOffreIndex);
            nextOffreIndex++;
        } else {
            nextOffreIndex = 0;
            secteurBases = null;
        }

        return nextSecteur;
    }

    private List<SecteurBase> fetchStudentDataFromAPI() {
        ResponseEntity<SecteurBase[]> response = restTemplate.getForEntity(apiUrl,
                SecteurBase[].class
        );
        SecteurBase[] secteurBases = response.getBody();
        log.info("info secteur {}", secteurBases);
        return Arrays.stream(secteurBases).filter(utils.distinctByKey(SecteurBase::getZip_sector)).collect(Collectors.toList());
    }

    private boolean studentDataIsNotInitialized() {
        return this.secteurBases == null;
    }
}
