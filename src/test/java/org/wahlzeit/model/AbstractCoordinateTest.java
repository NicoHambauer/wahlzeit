package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractCoordinateTest extends AbstractCoordinate {

    AbstractCoordinate abs_co;

    @Before //each
    public void init() {
        abs_co = new AbstractCoordinateTest();
    }

    @Test
    public void testSuperClass(){

    }


    //Stubs for testing
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return null;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return null;
    }

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
