package org.wahlzeit.model;

public class RealEstatePhotoManager extends PhotoManager {
    /**
     * @methodtype consturctor
     */
    public RealEstatePhotoManager() {
        photoTagCollector = RealEstatePhotoFactory.getInstance().createPhotoTagCollector();
    }

}
