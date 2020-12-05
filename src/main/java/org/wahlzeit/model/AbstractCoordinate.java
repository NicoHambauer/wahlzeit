package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate{

    public abstract CartesianCoordinate asCartesianCoordinate();

    public abstract SphericCoordinate asSphericCoordinate();

    public abstract void readFrom(ResultSet rset) throws SQLException;

    public abstract void  writeOn(ResultSet rset) throws SQLException;

    public abstract void writeId(PreparedStatement stmt, int pos) throws SQLException;

    @Override
    public double getCartesianDistance(Coordinate other_Coordinate) {
        CartesianCoordinate this_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesianCoordinate = other_Coordinate.asCartesianCoordinate();
        return this_coordinate.doGetDistance(other_cartesianCoordinate);
    }

    @Override
    public double getCentralAngle(Coordinate other_Coordinate) {
        SphericCoordinate this_spheric_coordinate = this.asSphericCoordinate();
        SphericCoordinate other_spheric_coordinate = other_Coordinate.asSphericCoordinate();//other_spheric_coordinate
        return this_spheric_coordinate.doGetCentralAngle(other_spheric_coordinate);
    }

    @Override
    public boolean equals(Object other_object) {
        if(!(Coordinate.class.isAssignableFrom(other_object.getClass())) || !(Coordinate.class.isAssignableFrom(this.getClass())) ) {
            return false;
        }
        Coordinate other_Coordinate = (Coordinate) other_object;
        return this.isEqual(other_Coordinate);
    }

    @Override
    public boolean isEqual(Coordinate other_Coordinate) {
        CartesianCoordinate this_cartesian_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesian_coordinate = other_Coordinate.asCartesianCoordinate();

        return this_cartesian_coordinate.doIsEqual(other_cartesian_coordinate);
    }

    /**
     *
     */
    @Override
    public String getIdAsString() {
        return null;
    }

}
