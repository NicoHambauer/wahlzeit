package org.wahlzeit.model;

public class RealEstate {

    RealEstatePhoto realEstatePhoto;
    RealEstateManager manager;
    RealEstateType type;

    public RealEstate(RealEstateType type, RealEstatePhoto realEstatePhoto, RealEstateManager manager){
        this.type = type;
        this.realEstatePhoto = realEstatePhoto;
        this.manager = manager;
    }
}
