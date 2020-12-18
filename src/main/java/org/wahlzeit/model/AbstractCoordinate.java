package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.CheckedCoordinateException;
import org.wahlzeit.utils.UncheckedCoordinateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate{

    public abstract CartesianCoordinate asCartesianCoordinate() throws CheckedCoordinateException;

    public abstract SphericCoordinate asSphericCoordinate() throws CheckedCoordinateException;

    public abstract void readFrom(ResultSet rset) throws SQLException;

    public abstract void  writeOn(ResultSet rset) throws SQLException;

    public abstract void writeId(PreparedStatement stmt, int pos) throws SQLException;

    /**
     * Part of Coordinate Interface
     * @param other_Coordinate
     * @return cartesian Distance from  this coordinate to other coordinate
     */
    @Override
    public double getCartesianDistance(Coordinate other_Coordinate) throws CheckedCoordinateException {
        assertClassInvariants();
        assertIsValidCoordinate(other_Coordinate);

        CartesianCoordinate this_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesianCoordinate = other_Coordinate.asCartesianCoordinate();
        double distance;
        try{
            distance = this_coordinate.doGetDistance(other_cartesianCoordinate);
        } catch (UncheckedCoordinateException un){
            throw new CheckedCoordinateException("Cartesian Distance was not calculated correctly", un);
        }

        assertIsValidCoordinate(this_coordinate);
        assertIsValidCoordinate(other_cartesianCoordinate);
        assertIsValidDistance(distance);
        assertClassInvariants();
        return distance;
    }

    /**
     * Part of Coordinate Interface
     * @param other_Coordinate
     * @return central angle in range 0 and 2*PI from this coordinate to other coordinate
     */
    @Override
    public double getCentralAngle(Coordinate other_Coordinate) throws CheckedCoordinateException {
        assertClassInvariants();
        assertIsValidCoordinate(other_Coordinate);

        SphericCoordinate this_spheric_coordinate = this.asSphericCoordinate();
        SphericCoordinate other_spheric_coordinate = other_Coordinate.asSphericCoordinate();//other_spheric_coordinate
        double centralAngle;
        try{
            centralAngle = this_spheric_coordinate.doGetCentralAngle(other_spheric_coordinate);
        } catch (UncheckedCoordinateException un){
            throw new CheckedCoordinateException("CentralAngle was not calculated correctly", un);
        }

        assertIsValidCoordinate(this_spheric_coordinate);
        assertIsValidCoordinate(other_spheric_coordinate);
        assertIsValidCentralAngle(centralAngle);
        assertClassInvariants();
        return centralAngle;
    }

    /**
     * Part of Coordinate Interface
     * @methodtype query comparison
     * @param other_Coordinate
     * @return true if this coordinate is equal to other coordinate
     */
    @Override
    public boolean isEqual(Coordinate other_Coordinate) throws CheckedCoordinateException {
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
     * @methodtype query comparison
     * @param other_object
     * @return
     */
    @Override
    public boolean equals(Object other_object)  {
        assertClassInvariants();
        assertObjectIsCoordinate(other_object);

        Coordinate other_Coordinate = (Coordinate) other_object;

        assertIsValidCoordinate(other_Coordinate);
        assertClassInvariants();
        boolean isEqual;
        try{
            isEqual = this.isEqual(other_Coordinate);
        } catch (CheckedCoordinateException ce){
            throw new RuntimeException("Calculation of isEquals went wrong. Coordinate failed.", ce);
        }
        return isEqual;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode(){
        assertClassInvariants();

        int hc;
        try{
            hc = this.asCartesianCoordinate().hashCode();
        } catch (CheckedCoordinateException ce){
            throw new RuntimeException("Calculation of HashCode went wrong. Coordinate failed.", ce);
        }

        assertIsValidHashCode(hc);
        assertClassInvariants();
        return hc;
    }

    /**
     *
     */
    @Override
    public String getIdAsString() {
        return null;
    }


    private void assertClassInvariants(){
        //no class invariants in the abstract class, only in subclasses
    }

    private void assertIsValidCoordinate(Coordinate co){
        if(co == null){
            throw new IllegalArgumentException("Argument Coordinate co hast to be != null");
        }
    }

    private void assertIsValidDistance(double distance){
        if(distance < 0){
            throw new RuntimeException("Calculated distance between Coordinates should be >= 0, but was:" + distance);
        }
    }

    private void assertIsValidCentralAngle(double angle){
        if (angle < Math.toRadians(0.0) || angle > Math.toRadians(360.0)){
            throw new RuntimeException("Central Angle should always be between 0 and 360 deg, but was:" + angle);
        }
    }

    private void assertObjectIsCoordinate(Object obj){
        if(obj == null){
            throw new IllegalArgumentException("The Object which was tried to compare was null");
        }
        if(!(Coordinate.class.isAssignableFrom(obj.getClass())) ) {
            throw new IllegalArgumentException("The Object which was tried to compare within the Coordinate Interface was not of type Coordinate");
        }
    }

    private void assertIsValidHashCode(int hc){
        if(hc < 0){
            throw new RuntimeException("HashCode invalid (<0), was:" + hc);
        }
    }

}
