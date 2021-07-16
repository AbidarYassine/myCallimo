package com.rest.ai.myCallimo.config.batch.annonceur;

import com.rest.ai.myCallimo.entities.AnnonceurEntity;
import com.rest.ai.myCallimo.entities.base.AnnonceurBase;
import org.springframework.batch.item.ItemProcessor;

public class AnnonceurBaseProcessor implements ItemProcessor<AnnonceurBase, AnnonceurEntity> {


    @Override
    public AnnonceurEntity process(AnnonceurBase annonceurBase) {
        AnnonceurEntity annonceurEntity = new AnnonceurEntity();
        annonceurEntity.setAnnonceur_id(annonceurBase.getAgencie_id());
        annonceurEntity.setAddress(annonceurBase.getOffer_agency_address());
        annonceurEntity.setName(annonceurBase.getOffer_agency_name());
        annonceurEntity.setEmail(annonceurBase.getOffer_agency_email());
        annonceurEntity.setTelephone(annonceurBase.getOffer_telephone());
        return annonceurEntity;
    }
}
