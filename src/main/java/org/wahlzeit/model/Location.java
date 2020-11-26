package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location extends DataObject {

    public CartesianCoordinate cartesianCoordinate;

    public Location(){
        this.cartesianCoordinate = new CartesianCoordinate();//standart coordinates (0,0,0) since there is no UI for coordinate input
        incWriteCount();
    }

    public Location(double x, double y, double z){
        this.cartesianCoordinate = new CartesianCoordinate(x, y, z);
        incWriteCount();
    }

    public CartesianCoordinate getCoordinate(){
        return this.cartesianCoordinate;
    }

    public void writeOn(ResultSet rset) throws SQLException {
        this.cartesianCoordinate.writeOn(rset);
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }

    public void readFrom(ResultSet rset) throws SQLException {
        this.cartesianCoordinate.readFrom(rset);
    }

    @Override
    public boolean isDirty(){
        boolean selfDirty = this.writeCount != 0;
        boolean coordinateDirty = this.cartesianCoordinate.isDirty();

        return selfDirty || coordinateDirty;
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    @Override
    public boolean equals(Object other_object) {
        //Location extends Object by standart in java
        if(!(other_object instanceof Location) || !(this instanceof Location)) {
            return false;
        }
        Location other_location = (Location) other_object;
        return this.cartesianCoordinate.isEqual(other_location.cartesianCoordinate);
    }

    @Override
    public int hashCode() {
        return this.cartesianCoordinate.hashCode();
    }
}
