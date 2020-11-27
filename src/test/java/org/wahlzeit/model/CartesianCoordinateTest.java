package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.DataObject;

import static org.junit.Assert.*;

public class CartesianCoordinateTest {

    private CartesianCoordinate co;
    private CartesianCoordinate other_co;
    private double max_diff = 0.00001;

    @Before //each
    public void init() {
        co = new CartesianCoordinate();
    }
    @Test
    public void testSuperClass(){
        assertTrue(CartesianCoordinate.class.getSuperclass() == DataObject.class);
    }

    @Test
    public void testNotNull(){
        //arrange in init
        //act
        //assert
        assertNotNull(co);
    }

    @Test
    public void testConstructor(){
        //arrange
        co = new CartesianCoordinate(10.0, 10.0, 10.0);
        //act
        double diff_x = Math.abs(co.getX() - 10.0);
        double diff_y = Math.abs(co.getY() - 10.0);
        double diff_z = Math.abs(co.getZ() - 10.0);
        //assert
        assertNotNull(co);
        assertTrue(diff_x <= max_diff);
        assertTrue(diff_y <= max_diff);
        assertTrue(diff_z <= max_diff);
    }

    @Test
    public void testGetMethods(){
        //arrange
        //act
        double diff_x = Math.abs(co.getX() - 0.0);
        double diff_y = Math.abs(co.getY() - 0.0);
        double diff_z = Math.abs(co.getZ() - 0.0);
        //assert
        assertTrue(diff_x <= max_diff);
        assertTrue(diff_y <= max_diff);
        assertTrue(diff_z <= max_diff);
    }

    @Test
    public void testSetMethods(){
        //arrange
        //act
        co.setX(10.0);
        co.setY(20.0);
        co.setZ(5.0);
        double diff_x = Math.abs(co.getX() - 10.0);
        double diff_y = Math.abs(co.getY() - 20.0);
        double diff_z = Math.abs(co.getZ() - 5.0);
        //assert
        assertTrue(diff_x <= max_diff);
        assertTrue(diff_y <= max_diff);
        assertTrue(diff_z <= max_diff);
    }

    @Test
    public void testDistance(){
        //arrange
        other_co = new CartesianCoordinate(3.0, 3.0, 1.5);
        //act
        double distance = co.getCartesianDistance(other_co);
        //assert
        assertTrue(Math.abs(distance - 4.5) <= max_diff);

    }

    @Test
    public void testEqualsSimple(){
        //arrange
        other_co = new CartesianCoordinate();
        //act
        //assert
        assertTrue(co.equals(other_co));

    }

    @Test
    public void testEquals(){
        //arrange
        other_co = new CartesianCoordinate(1.0,1.0,1.0);
        //act
        co.setX(1.0);
        co.setY(1.0);
        co.setZ(1.0);
        //assert
        assertTrue(co.equals(other_co));

    }

    @Test
    public void testHashCode(){
        //arrange
        other_co = new CartesianCoordinate(1.0,1.0,1.0);
        //act
        co.setX(1.0);
        co.setY(1.0);
        co.setZ(1.0);
        //assert
        assertEquals(co.hashCode(), other_co.hashCode());

    }

}
