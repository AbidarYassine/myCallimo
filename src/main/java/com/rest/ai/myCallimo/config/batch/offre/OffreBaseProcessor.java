package com.rest.ai.myCallimo.config.batch.offre;


import com.rest.ai.myCallimo.dto.AnnonceurDto;
import com.rest.ai.myCallimo.dto.CategoryDto;
import com.rest.ai.myCallimo.dto.CityDto;
import com.rest.ai.myCallimo.dto.OffreTypeDto;
import com.rest.ai.myCallimo.entities.*;
import com.rest.ai.myCallimo.entities.base.OffreBase;
import com.rest.ai.myCallimo.services.facade.*;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class OffreBaseProcessor implements ItemProcessor<OffreBase, OffreEntity> {


    //    get category by name   done ;
//    get annonceur by name or by telephone Done
//    get city by name Done
//    get offre type
    @Autowired
    private CityService cityService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AnnonceurService annonceurService;
    @Autowired
    private OffreTypeService offreTypeService;
    @Autowired
    private SecteurService secteurService;

    @Override
    public OffreEntity process(OffreBase offreBase) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        /* get Entities */
        CityEntity cityEntity = modelMapper.map(cityService.findByName(offreBase.getOffer_city()), CityEntity.class);

        /* manage secteur and cities */
        Secteur secteur = modelMapper.map(secteurService.findByCode(offreBase.getZip_sector()), Secteur.class);
        cityEntity.setSecteur(secteur);

        cityService.save(modelMapper.map(cityEntity, CityDto.class));
        /*secteurService.save(modelMapper.map(secteur, SecteurDto.class));*/
        /* end */


        CategoryDto categoryDto = categoryService.findByName(offreBase.getOffer_category());
        CategoryEntity categoryEntity = null;
        if (categoryDto != null)
            categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);

        AnnonceurDto annonceurDto = annonceurService.findByTelephone(offreBase.getOffer_telephone());
        AnnonceurEntity annonceurEntity = null;
        if (annonceurDto != null)
            annonceurEntity = modelMapper.map(annonceurDto, AnnonceurEntity.class);


        OffreTypeDto offreTypeDto = offreTypeService.findByType(offreBase.getOffer_type());
        OffreTypeEntity offreTypeEntity = null;
        if (offreTypeDto != null)
            offreTypeEntity = modelMapper.map(offreTypeDto, OffreTypeEntity.class);


        OffreEntity offreEntity = new OffreEntity();
//        set Entities
        offreEntity.setAnnonceur(annonceurEntity);
        offreEntity.setCategory(categoryEntity);
        offreEntity.setCity(cityEntity);
        offreEntity.setOffre_type(offreTypeEntity);


//        set Attribute;
        offreEntity.setLib_sector(offreBase.getLib_sector());
        offreEntity.setZip_sector(offreBase.getZip_sector());
        offreEntity.setAddress(offreBase.getOffer_address());
        offreEntity.setArea(offreBase.getOffer_area());
        offreEntity.setBathroom(offreBase.getBathroom());
        offreEntity.setArea_units(offreBase.getOffer_area_units());
        offreEntity.setChamber(offreBase.getOffer_chamber());
        offreEntity.setCurrency(offreBase.getOffer_currency());
        offreEntity.setDepartment(offreBase.getDepartment());
        offreEntity.setDescription(offreBase.getOffer_description());
        offreEntity.setFirst_price(offreBase.getOffer_first_price());
        offreEntity.setIs_active(offreBase.getIs_active());
        offreEntity.setIs_new(offreBase.getIs_new());
        offreEntity.setIs_processed(offreBase.getIs_processed());
        offreEntity.setIs_update_area(offreBase.getIs_update_area());
        offreEntity.setIs_update_price(offreBase.getIs_update_price());
        offreEntity.setIs_url_exist(offreBase.getIs_url_exist());
        offreEntity.setUrl(offreBase.getOffer_url());
        offreEntity.setOffer_status(offreBase.getOffer_status());
        offreEntity.setOffer_code_status(offreBase.getOffer_code_status());
        offreEntity.setTitle(offreBase.getOffer_title());
        offreEntity.setReference(offreBase.getOffer_reference());
        offreEntity.setLast_check_date(offreBase.getLast_check_date());
        offreEntity.setOffer_dpe(offreBase.getOffer_dpe());
        offreEntity.setOffer_ges(offreBase.getOffer_ges());
        offreEntity.setNew_price_date(offreBase.getNew_price_date());
        offreEntity.setOffer_latitude(offreBase.getOffer_latitude());
        offreEntity.setSurface_land(offreBase.getSurface_land());
        offreEntity.setOffer_longitude(offreBase.getOffer_longitude());
        offreEntity.setOffer_rank_max(offreBase.getOffer_rank_max());
        offreEntity.setOffer_rank_obtained(offreBase.getOffer_rank_obtained());
        offreEntity.setPices(offreBase.getOffer_pices());
        offreEntity.setPiscine(offreBase.getOffer_piscine());
        return offreEntity;

    }
}
