package com.rest.ai.myCallimo.config.batch.secteur;

import com.rest.ai.myCallimo.entities.Secteur;
import com.rest.ai.myCallimo.entities.base.SecteurBase;
import org.springframework.batch.item.ItemProcessor;

public class SecteurProcess implements ItemProcessor<SecteurBase, Secteur> {
    @Override
    public Secteur process(SecteurBase secteurBase) throws Exception {
        Secteur secteur = new Secteur();
        secteur.setCode(secteurBase.getZip_sector());
        secteur.setLibelle(secteurBase.getLib_sector());
        return secteur;
    }
}
