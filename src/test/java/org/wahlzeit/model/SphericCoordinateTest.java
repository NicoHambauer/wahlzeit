package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.CheckedCoordinateException;

import static org.junit.Assert.*;

public class SphericCoordinateTest {

    private SphericCoordinate co;
    private SphericCoordinate other_co;
    private double max_diff = 0.000001;

    @Before //each
    public void init() {
        //arrange
        co = new SphericCoordinate();//origin quals (0,0,0)
    }
    @Test
    public void testSuperClass(){
        assertTrue(SphericCoordinate.class.getSuperclass() == AbstractCoordinate.class);
    }

    @Test
    public void testConstructor(){
        co = new SphericCoordinate(10.0,(Math.PI / 2.0) * (1.0/3.0),(Math.PI / 2.0) * (2.0/3.0));
        //act
        double diff_r = Math.abs(co.getRadius() - 10.0);
        double diff_t = Math.abs(co.getTheta() - (Math.PI / 2.0) * (1.0/3.0));
        double diff_p = Math.abs(co.getPhi() - (Math.PI / 2.0) * (2.0/3.0));
        //assert
        assertTrue(diff_r <= max_diff);
        assertTrue(diff_t <= max_diff);
        assertTrue(diff_p <= max_diff);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorIllegalTheta(){
        co = new SphericCoordinate(10.0, Math.PI * 1.5 , 0.0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testConstructorIllegalPhi(){
        co = new SphericCoordinate(10.0, 0.0 , Math.PI * 3);
    }

    @Test
    public void testDistance(){
        //arrange also see init
        other_co = new SphericCoordinate(5.0, (Math.PI / 2.0) * (2.0/3.0), 0.0);
        //act
        double distance = 0.0;
        try{
            distance = co.getCartesianDistance(other_co);
        } catch (CheckedCoordinateException ce){
            ce.printStackTrace();
        }
        //assert
        assertTrue(Math.abs(distance - 5.0) <= max_diff);
    }

    @Test
    public void testCartesianConversion(){
        //arrange
        other_co = new SphericCoordinate(5.0, 0.927295218, Math.PI / 2.0);
        //act
        CartesianCoordinate coordinate = co.asCartesianCoordinate();
        CartesianCoordinate other_coordinate = other_co.asCartesianCoordinate();
        //assert
        assertTrue( Math.abs(coordinate.getX() - 0.0) <= max_diff);
        assertTrue( Math.abs(coordinate.getY() - 0.0) <= max_diff);
        assertTrue( Math.abs(coordinate.getZ() - 0.0) <= max_diff);


        assertTrue(Math.abs(other_coordinate.getY() - 4.0) <= max_diff);
        assertTrue(Math.abs(other_coordinate.getZ() - 3.0) <= max_diff);
    }

    @Test
    public void testSphericConversion(){
        //arrange
        other_co = new SphericCoordinate(5.0, Math.toRadians(60.0), Math.PI / 2.0);
        //act
        SphericCoordinate same_coordinate = other_co.asSphericCoordinate();
        //assert
        assertTrue(other_co.equals(same_coordinate));
        assertEquals(other_co, same_coordinate); //since .asSphericCoordinate should only return this, even objects are the same
    }

    @Test
    public void testCentralAngle(){
        //arrange
        other_co = new SphericCoordinate(9.0, Math.toRadians(35.0), Math.PI / 2.0);
        SphericCoordinate second_co = new SphericCoordinate(9.0, Math.toRadians(30.0), Math.PI * 1.5);
        double central_angle = 0.0;
        try{
            central_angle = other_co.getCentralAngle(second_co);
        } catch (CheckedCoordinateException ce){
            ce.printStackTrace();
        }
        assertTrue( Math.abs(central_angle - (Math.toRadians(65.0))) <= max_diff);
    }

    @Test
    public void testEquals(){
        other_co = new SphericCoordinate(0, 0, 0);
        SphericCoordinate c3 = new SphericCoordinate(7.5, Math.toRadians(45.0), Math.toRadians(30.0));
        SphericCoordinate c4 = new SphericCoordinate(7.5, Math.toRadians(45.0), Math.toRadians(30.0));
        SphericCoordinate c5 = new SphericCoordinate(4.0, Math.toRadians(60.0), Math.toRadians(90.0));
        //assert
        assertTrue(co.equals(other_co));
        assertTrue(c3.equals(c4));
        assertFalse(c3.equals(c5));
    }
}
