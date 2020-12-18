package org.wahlzeit.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.wahlzeit.utils.CheckedCoordinateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractCoordinateTest extends AbstractCoordinate {

    AbstractCoordinate abs_co1;
    AbstractCoordinate abs_co2;

    double x, y, z;

    double to_be_distance = 1.0;
    double to_be_central_angle = Math.toRadians(45.0);
    boolean to_be_equals = true;

    double maxdiff = 0.00001;

    public AbstractCoordinateTest(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Before //each
    public void init() {
        abs_co1 = new AbstractCoordinateTest();
        abs_co2 = new AbstractCoordinateTest();
    }

    @Test
    public void testCartesianDistance(){
        //arrange see init act
        double distance = 0.0;
        try{
            distance = abs_co1.getCartesianDistance(abs_co2);
        } catch (CheckedCoordinateException ce){
            ce.printStackTrace();
        }
        //assert
        assertTrue(Math.abs(distance - to_be_distance) <= maxdiff);
    }

    @Test
    public void testCentralAngle(){
        //arrange see init act
        double central_angle = 0.0;
        try{
            central_angle = abs_co1.getCentralAngle(abs_co2);
        } catch (CheckedCoordinateException ce){
            ce.printStackTrace();
        }
        //assert
        assertTrue(Math.abs(central_angle - to_be_central_angle) <= maxdiff);
    }

    @Test
    public void testEqualsAndIsEqual(){
        //arrange see init and act
        boolean eq = abs_co1.equals(abs_co2);
        //assert
        assertTrue(eq);
    }


    //Stubs for testing
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return new CartesianCoordinate(){
            //stub with stub methods
            public double doGetDistance(CartesianCoordinate other_cartesian_Coordinate){
                return to_be_distance;
            }

            public boolean doIsEqual(CartesianCoordinate other_cartesianCoordinate){
                return to_be_equals;
            }
        };
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return new SphericCoordinate(){
            //stub with stub methods
            public double doGetCentralAngle(SphericCoordinate other_spheric_Coordinate){
                return to_be_central_angle;
            }
        };
    }

    //Other Methods not important, since they ar Subclass specific and abstract in AbstractCoordinate
    @Override
    public void readFrom(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {

    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }
}
