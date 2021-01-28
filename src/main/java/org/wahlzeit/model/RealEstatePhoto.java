package org.wahlzeit.model;

import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.utils.DesignPatternInstance;
import org.wahlzeit.utils.StringUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

@DesignPatternInstance(
        purpose = "Creational",
        scope = "Object",
        patternName = "Abtract Factory",
        patternParticipants = {"Abstract Factory", "Concrete Factory", "Abstract Product", "Concrete Product"},
        instanceParticipants = {"PhotoFactory", "RealEstatePhotoFactory", "Photo", "RealEstatePhoto"},
        roleOfAnnotatedClass = "Concrete Product"
)
public class RealEstatePhoto extends Photo {

    RealEstate building;
    RealEstatePhotoManager manager;

    protected int rooms = 5;
    protected int bathrooms = 2;
    protected int year_built = 1996;
    protected boolean renovated = false;


    /**
     * @methodtype constructor
     */
    public RealEstatePhoto() {
        id = PhotoId.getNextId();
        incWriteCount();
        //Object Creation: This Constructor is called from RealEstatePhotoFactory#createPhoto()
        //					So Instantiation Process is as follows: RealEstatePhotoFactory#createPhoto() --> RealEstatePhoto#RealEstatePhoto()
        //					(Design) Patterns of Object Creation: "Abstract Factory" ( RealEstatePhotoFactory#createPhoto() {... return new RealEstatePhoto(..);}
        //                  Base level (0): Here Object RealEstatePhoto is created by constructor (Step 3)
    }

    /**
     *
     * @methodtype constructor
     */
    public RealEstatePhoto(PhotoId myId) {
        id = myId;
        incWriteCount();
        //Object Creation: This Constructor is called from RealEstatePhotoFactory#createPhoto()
        //					So Instantiation Process is as follows: RealEstatePhotoFactory#createPhoto() --> RealEstatePhoto#RealEstatePhoto()
        //					(Design) Patterns of Object Creation: "Abstract Factory" ( RealEstatePhotoFactory#createPhoto() {... return new RealEstatePhoto(..);}
        //                  Base level (0): Here Object RealEstatePhoto is created by constructor (Step 3)
    }

    /**
     *
     * @methodtype constructor
     */
    public RealEstatePhoto(ResultSet rset) throws SQLException {
        readFrom(rset);
        //Object Creation: This Constructor is called from RealEstatePhotoFactory#createPhoto()
        //					So Instantiation Process is as follows: RealEstatePhotoFactory#createPhoto() --> RealEstatePhoto#RealEstatePhoto()
        //					(Design) Patterns of Object Creation: "Abstract Factory" ( RealEstatePhotoFactory#createPhoto() {... return new RealEstatePhoto(..);}
        //                  Base level (0): Here Object RealEstatePhoto is created by constructor (Step 3)
    }

    /**
     *
     */
    public void readFrom(ResultSet rset) throws SQLException {
        super.readFrom(rset);
        rooms = rset.getInt("rooms");
        bathrooms = rset.getInt("bathrooms");
        year_built = rset.getInt("year_built");
        renovated = rset.getBoolean("renovated");
    }

    /**
     *
     */
    public void writeOn(ResultSet rset) throws SQLException {
        super.writeOn(rset);
        rset.updateInt("rooms", rooms);
        rset.updateInt("bathrooms", bathrooms);
        rset.updateInt("year_built", year_built);
        rset.updateBoolean("renovated", renovated);
    }

    /**
     *
     * @methodtype get
     */

    public int getRooms(){
        return this.rooms;
    }

    /**
     *
     * @methodtype get
     */
    public int getBathrooms(){
        return this.bathrooms;
    }

    /**
     *
     * @methodtype get
     */
    public int getYear_built(){
        return this.year_built;
    }

    /**
     *
     * @methodtype get
     */
    public boolean isRenovated(){
        return this.renovated;
    }

    /**
     *
     * @methodtype set
     */
    public void setRooms(int rooms){
        this.rooms = rooms;
        incWriteCount();
    }

    /**
     *
     * @methodtype set
     */
    public void setBathrooms(int bathrooms){
        this.bathrooms = bathrooms;
        incWriteCount();
    }

    /**
     *
     * @methodtype set
     */
    public void setYear_built(int year_built){
        this.year_built = year_built;
        incWriteCount();
    }

    /**
     *
     * @methodtype set
     */
    public void setRenovated(boolean renovated){
        this.renovated = renovated;
        incWriteCount();
    }

    /**
     *
     * @methodtype boolean-query
     */
    public boolean isOlderThan(RealEstatePhoto other){
        return this.year_built < other.year_built;
    }
}
