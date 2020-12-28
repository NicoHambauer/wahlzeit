package org.wahlzeit.model;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.wahlzeit.utils.CheckedCoordinateException;
import org.wahlzeit.utils.UncheckedCoordinateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AbstractCoordinateTest extends AbstractCoordinate {

    public AbstractCoordinate abs_co1;
    public AbstractCoordinate abs_co2;

    public static boolean testException;

    public double x, y, z;

    public double to_be_distance = 1.0;
    public double to_be_central_angle = Math.toRadians(45.0);
    public boolean to_be_equals = true;

    public double maxdiff = 0.00001;

    public AbstractCoordinateTest(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
    }


    @Before //each
    public void init() {
        this.testException = false;
        this.abs_co1 = new AbstractCoordinateTest();
        this.abs_co2 = new AbstractCoordinateTest();
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

    @Test (expected = CheckedCoordinateException.class)
    public void testCartesianDistanceException() throws CheckedCoordinateException {
        //arrange see init act
        double distance = 0.0;
        testException = true;
        distance = abs_co1.getCartesianDistance(abs_co2);

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

    @Test (expected = CheckedCoordinateException.class)
    public void testCentralAngleException() throws CheckedCoordinateException{
        //arrange see init act
        testException = true;
        double central_angle = 0.0;
        central_angle = abs_co1.getCentralAngle(abs_co2);

    }

    @Test
    public void testEqualsAndIsEqual(){
        //arrange see init and act
        boolean eq = abs_co1.equals(abs_co2);
        //assert
        assertTrue(eq);
    }

    @Test (expected = CheckedCoordinateException.class)
    public void testIsEqualException() throws CheckedCoordinateException {
        //arrange see init and act
        testException = true;
        boolean eq = abs_co1.isEqual(abs_co2);
    }


    //Stubs for testing
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        //Using Mockito, see imports/dependencies from build.gradle: testCompile 'org.mockito:mockito-core:2.+'
        CartesianCoordinate cartesianMock = mock(CartesianCoordinate.class);

        if(testException){
            doThrow(new UncheckedCoordinateException("doGetDistance failed")).when(cartesianMock.doGetDistance(any(CartesianCoordinate.class)));
            doThrow(new UncheckedCoordinateException("diIsEqual went wrong")).when(cartesianMock.doIsEqual(any(CartesianCoordinate.class)));
        } else {
            when(cartesianMock.doGetDistance(any(CartesianCoordinate.class))).thenReturn(to_be_distance);
            when(cartesianMock.doIsEqual(any(CartesianCoordinate.class))).thenReturn(to_be_equals);
        }
        return cartesianMock;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        //Using Mockito, see imports/dependencies from build.gradle: testCompile 'org.mockito:mockito-core:2.+'
        SphericCoordinate sphericMock = mock(SphericCoordinate.class);

        if(testException){
            doThrow(new UncheckedCoordinateException("doGetCentralAngle failed")).when(sphericMock.doGetCentralAngle(any(SphericCoordinate.class)));
        } else {
            when(sphericMock.doGetCentralAngle(any(SphericCoordinate.class))).thenReturn(to_be_central_angle);
        }

        return sphericMock;
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
