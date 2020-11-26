package org.wahlzeit.model;


import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.DataObject;

import static org.junit.Assert.*;

public class LocationTest {

    private Location loc;
    private Location other_loc;

    @Before //each
    public void init() {
        loc = new Location();
    }

    @Test
    public void testSuperClass(){
        assertTrue(CartesianCoordinate.class.getSuperclass() == DataObject.class);
    }

    @Test
    public void testLocationCoordinateNotNull() {
        //arrange
        CartesianCoordinate c = loc.getCoordinate();
        //act
        //assert
        assertNotNull(c);
    }

    @Test
    public void testLocationIsDirty() {
        //arrange see init
        //act
        //assert
        assertTrue(loc.isDirty());
    }

    @Test
    public void testLocationEqualsOther() {
        //arrange
        other_loc = new Location();
        //act
        //assert
        assertTrue(loc.equals(other_loc));
    }

    @Test
    public void testLocationNotEqualsOther() {
        //arrange
        other_loc = new Location(10,10,10);
        //act
        //assert
        assertFalse(loc.equals(other_loc));
    }

    @Test
    public void testLocationHashCodes() {
        //arrange
        other_loc = new Location();
        //act
        //assert
        assertEquals(loc.hashCode(), other_loc.hashCode());
    }

}
