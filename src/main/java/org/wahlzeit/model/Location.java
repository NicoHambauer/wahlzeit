package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Location extends DataObject {

    public Coordinate coordinate;

    public Location(){
        this.coordinate = new Coordinate();//standart coordinates (0,0,0) since there is no UI for coordinate input
        incWriteCount();
    }

    public Location(double x, double y, double z){
        this.coordinate = new Coordinate(x, y, z);
        incWriteCount();
    }

    public Coordinate getCoordinate(){
        return this.coordinate;
    }

    public void writeOn(ResultSet rset) throws SQLException {
        this.coordinate.writeOn(rset);
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }

    public void readFrom(ResultSet rset) throws SQLException {
        this.coordinate.readFrom(rset);
    }

    @Override
    public boolean isDirty(){
        boolean selfDirty = this.writeCount != 0;
        boolean coordinateDirty = this.coordinate.isDirty();

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
        return this.coordinate.isEqual(other_location.coordinate);
    }

    @Override
    public int hashCode() {
        return this.coordinate.hashCode();
    }
}
