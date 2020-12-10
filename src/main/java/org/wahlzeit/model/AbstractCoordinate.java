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
        assertClassInvariants();
        assertIsValidCoordinate(other_Coordinate);

        CartesianCoordinate this_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesianCoordinate = other_Coordinate.asCartesianCoordinate();
        double distance = this_coordinate.doGetDistance(other_cartesianCoordinate);

        assertIsValidCoordinate(this_coordinate);
        assertIsValidCoordinate(other_cartesianCoordinate);
        assertIsValidDistance(distance);

        assertClassInvariants();
        return distance;
    }

    @Override
    public double getCentralAngle(Coordinate other_Coordinate) {
        assertClassInvariants();
        assertIsValidCoordinate(other_Coordinate);

        SphericCoordinate this_spheric_coordinate = this.asSphericCoordinate();
        SphericCoordinate other_spheric_coordinate = other_Coordinate.asSphericCoordinate();//other_spheric_coordinate
        double centralAngle = this_spheric_coordinate.doGetCentralAngle(other_spheric_coordinate);

        assertIsValidCoordinate(this_spheric_coordinate);
        assertIsValidCoordinate(other_spheric_coordinate);
        assertIsValidCentralAngle(centralAngle);

        assertClassInvariants();
        return centralAngle;
    }

    @Override
    public boolean equals(Object other_object) {
        assertClassInvariants();
        assertObjectIsCoordinate(other_object);

        Coordinate other_Coordinate = (Coordinate) other_object;

        assertIsValidCoordinate(other_Coordinate);
        assertClassInvariants();
        return this.isEqual(other_Coordinate);
    }

    @Override
    public int hashCode() {
        assertClassInvariants();

        int hc = this.asCartesianCoordinate().hashCode();

        assertIsValidHashCode(hc);
        assertClassInvariants();
        return hc;
    }

    @Override
    public boolean isEqual(Coordinate other_Coordinate) {
        assertClassInvariants();
        assertIsValidCoordinate(other_Coordinate);

        CartesianCoordinate this_cartesian_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesian_coordinate = other_Coordinate.asCartesianCoordinate();

        assertIsValidCoordinate(this_cartesian_coordinate);
        assertIsValidCoordinate(other_cartesian_coordinate);

        assertClassInvariants();

        return this_cartesian_coordinate.doIsEqual(other_cartesian_coordinate);
    }

    /**
     *
     */
    @Override
    public String getIdAsString() {
        return null;
    }


    public void assertClassInvariants(){
        //no class invariants in the abstract class, only in subclasses
    }

    public void assertIsValidCoordinate(Coordinate co){
        if(co == null){
            throw new IllegalArgumentException("Argument Coordinate co hast to be != null");
        }
    }

    public void assertIsValidDistance(double distance){
        if(distance < 0){
            throw new RuntimeException("Calculated distance between Coordinates should be >= 0, but was:" + distance);
        }
    }

    public void assertIsValidCentralAngle(double angle){
        if (angle < Math.toRadians(0.0) || angle > Math.toRadians(360.0)){
            throw new RuntimeException("Central Angle should always be between 0 and 360 deg, but was:" + angle);
        }
    }

    public void assertObjectIsCoordinate(Object obj){
        if(obj == null){
            throw new IllegalArgumentException("The Object which was tried to compare was null");
        }
        if(!(Coordinate.class.isAssignableFrom(obj.getClass())) ) {
            throw new IllegalArgumentException("The Object which was tried to compare within the Coordinate Interface was not of type Coordinate");
        }
    }

    public void assertIsValidHashCode(int hc){
        if(hc < 0){
            throw new RuntimeException("HashCode invalid (<0), was:" + hc);
        }
    }

}
