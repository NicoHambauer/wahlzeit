package org.wahlzeit.model;

import org.wahlzeit.utils.DesignPatternInstance;

import java.sql.ResultSet;
import java.sql.SQLException;


@DesignPatternInstance(
        purpose = "Creational",
        scope = "Object",
        patternName = "Abtract Factory",
        patternParticipants = {"Abstract Factory", "Concrete Factory", "Abstract Product", "Concrete Product"},
        instanceParticipants = {"PhotoFactory", "RealEstatePhotoFactory", "Photo", "RealEstatePhoto"},
        roleOfAnnotatedClass = "Concrete Factory"
)
public class RealEstatePhotoFactory extends PhotoFactory {


    /**
     *
     */
    protected RealEstatePhotoFactory() {
        // do nothing
    }

    /**
     * @methodtype factory
     */
    public RealEstatePhoto createPhoto() {
        return new RealEstatePhoto();//does not need to be changed to RealEstatePhotoFactory since the method is overwritten
    }

    /**
     * @methodtype factory
     */
    public RealEstatePhoto createPhoto(PhotoId id) {
        return new RealEstatePhoto(id);
    }

    /**
     * @methodtype factory
     */
    public RealEstatePhoto createPhoto(ResultSet rs) throws SQLException {
        return new RealEstatePhoto(rs);
    }

}
