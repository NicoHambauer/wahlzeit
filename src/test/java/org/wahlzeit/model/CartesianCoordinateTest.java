package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.CheckedCoordinateException;
import org.wahlzeit.utils.UncheckedCoordinateException;

import static org.junit.Assert.*;

public class CartesianCoordinateTest {

    private CartesianCoordinate co;
    private CartesianCoordinate other_co;
    private double max_diff = 0.00001;

    @Before //each
    public void init() {
        co = CartesianCoordinate.getOrCreateCartesianCoordinate();
    }
    @Test
    public void testSuperClass(){

        assertTrue(CartesianCoordinate.class.getSuperclass() == AbstractCoordinate.class);
    }

    @Test
    public void testNotNull(){
        //arrange in init
        //act
        //assert
        assertNotNull(co);
    }

    @Test
    public void testCreation(){
        //arrange
        co = CartesianCoordinate.getOrCreateCartesianCoordinate(10.0, 10.0, 10.0);
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
    public void testGetOrCreateUsesShared(){
        //arrange
        //act
        co = CartesianCoordinate.getOrCreateCartesianCoordinate(10.0, 20.0, 5.0);
        other_co = CartesianCoordinate.getOrCreateCartesianCoordinate(10.0, 20.0, 5.0);

        //assert
        //since ValueObjects are shared they also share the same reference
        assertTrue(co.equals(other_co));

    }

    @Test
    public void testDistance(){
        //arrange
        other_co = CartesianCoordinate.getOrCreateCartesianCoordinate(3.0, 3.0, 1.5);
        //act
        double distance = 0.0;
        try{
            distance = co.getCartesianDistance(other_co);
        } catch (CheckedCoordinateException ce){
            ce.printStackTrace();
        }
        //assert
        assertTrue(Math.abs(distance - 4.5) <= max_diff);

    }

    @Test
    public void testAsCartesianCoordinate(){
        //arrange
        co = CartesianCoordinate.getOrCreateCartesianCoordinate(3.0, 3.0, 1.5);
        //act

        //assert
        assertSame(co, co.asCartesianCoordinate());

    }

    @Test
    public void testEqualsSimple(){
        //arrange
        other_co = CartesianCoordinate.getOrCreateCartesianCoordinate();
        //act
        //assert
        assertTrue(co.equals(other_co));

    }

    @Test
    public void testEquals(){
        //arrange
        co = CartesianCoordinate.getOrCreateCartesianCoordinate(1.0, 1.0, 1.0);
        other_co = CartesianCoordinate.getOrCreateCartesianCoordinate(1.0,1.0,1.0);
        //act
        //assert
        assertTrue(co.equals(other_co));

    }

    @Test
    public void testHashCode(){
        //arrange
        co = CartesianCoordinate.getOrCreateCartesianCoordinate(1.0, 1.0, 1.0);
        other_co = CartesianCoordinate.getOrCreateCartesianCoordinate(1.0,1.0,1.0);
        //act
        //assert
        assertEquals(co.hashCode(), other_co.hashCode());

    }

    @Test (expected = IllegalArgumentException.class)
    public void testIllegalArgument(){
        //arrange
        other_co = null;
        //act
        //test/assert
        co.equals(other_co);

    }

    @Test (expected = UncheckedCoordinateException.class)
    public void testUncheckedCoordinateException(){
        //arrange
        other_co = CartesianCoordinate.getOrCreateCartesianCoordinate(Double.NaN,1.0,1.0);
        //act
        //test/assert
        co.equals(other_co);

    }

}
