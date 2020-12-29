/*
 * Copyright (c) 2020 by Nico Hambauer, https://github.com/NicoHambauer
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;
import org.wahlzeit.utils.CheckedCoordinateException;
import org.wahlzeit.utils.UncheckedCoordinateException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractCoordinate extends DataObject implements Coordinate{


    //Value Object Representation and Sharing Values
    public static HashMap<Integer, Coordinate> coordinates = new HashMap<>();

    public abstract CartesianCoordinate asCartesianCoordinate();

    public abstract SphericCoordinate asSphericCoordinate();

    public abstract void readFrom(ResultSet rset) throws SQLException;

    public abstract void  writeOn(ResultSet rset) throws SQLException;

    public abstract void writeId(PreparedStatement stmt, int pos) throws SQLException;

    /**
     * Part of Coordinate Interface
     * @throws CheckedCoordinateException
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
     * @throws CheckedCoordinateException
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
     * @throws CheckedCoordinateException
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

        boolean isEqual;
        try{
            isEqual = this_cartesian_coordinate.doIsEqual(other_cartesian_coordinate);
        } catch (UncheckedCoordinateException un){
            throw new CheckedCoordinateException("Calculation of Equality failed. Coordinate failed.", un);
        }

        return isEqual;
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

        int hc = this.asCartesianCoordinate().hashCode();

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
            throw new UncheckedCoordinateException("Calculated distance between Coordinates should be >= 0, but was:" + distance);
        }
    }

    private void assertIsValidCentralAngle(double angle){
        if (angle < Math.toRadians(0.0) || angle > Math.toRadians(360.0)){
            throw new UncheckedCoordinateException("Central Angle should always be between 0 and 360 deg, but was:" + angle);
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
        //All HashCodes even negative ones are valid
        /*
        if(hc < 0){
            throw new UncheckedCoordinateException("HashCode invalid (<0), was:" + hc);
        }
        */
    }

}
