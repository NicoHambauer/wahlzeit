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
import org.wahlzeit.utils.UncheckedCoordinateException;

import javax.mail.MethodNotSupportedException;
import javax.xml.namespace.QName;
import java.awt.geom.Arc2D;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class CartesianCoordinate extends AbstractCoordinate {

    private final double x, y, z;

    /**
     * Value Object Representation and Sharing Values in AbstractCoordinate
     * @methodtype
     * @return If Coordinate with the values x, y, z already exists returns that specific CartesianCoordinate, else
     *         returns new CartesianCoordinate
     */
    public static CartesianCoordinate getOrCreateCartesianCoordinate(double x, double y, double z){
        CartesianCoordinate cc = new CartesianCoordinate(x, y, z);
        Coordinate coordinateAlreadyStored = coordinates.putIfAbsent(cc.hashCode(), cc);
        if (coordinateAlreadyStored == null){
            //no coordinate stored for this key yet --> creates coordinate
            return cc;
        }
        return coordinates.get(cc.hashCode()).asCartesianCoordinate();
    }

    /**
     * Value Object Representation and Sharing Values in AbstractCoordinate
     * @methodtype
     * @return If Coordinate with the values x, y, z already exists returns that specific CartesianCoordinate, else
     *         returns new CartesianCoordinate
     */
    public static CartesianCoordinate getOrCreateCartesianCoordinate(){
        return getOrCreateCartesianCoordinate(0.0, 0.0, 0.0);
    }

    /**
     * Value Object Representation so constructor is hidden
     * CartesianCoordinate standart Constructor initializes a Coordinate with x, y, z = 0.0
     * @methodtype constructor
     */
    private CartesianCoordinate(){
        this(0.0, 0.0, 0.0);
    }
    /**
     * Value Object Representation so constructor is hidden
     * CartesianCoordinate Constructor
     * @methodtype constructor
     */
    private CartesianCoordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        assertClassInvariants();
        return x;
    }

    public double getY() {
        assertClassInvariants();
        return y;
    }

    public double getZ() {
        assertClassInvariants();
        return z;
    }

    @Override
    public void writeOn(ResultSet rset) throws SQLException {
        assertClassInvariants();
        assertIsNotNull(rset);
        assertIsNotNull(rset);
        rset.updateDouble("coordinate_x", getX());
        rset.updateDouble("coordinate_y", getY());
        rset.updateDouble("coordinate_z", getZ());
        assertClassInvariants();
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }

    @Override
    public void readFrom(ResultSet rset) throws SQLException {
        // Since object is immutable and the method is called by this.readFrom there is no option to assign this any of
        // x, y or z values
        //Execution will always fail in Value Object Representation, which is ok since coordinates should not change/are immutable
        assertClassInvariants();
        assertIsNotNull(rset);
        double coordinate_x = rset.getDouble("coordinate_x");
        double coordinate_y = rset.getDouble("coordinate_y");
        double coordinate_z = rset.getDouble("coordinate_z");
        CartesianCoordinate newCartesianCoordinate = getOrCreateCartesianCoordinate(coordinate_x, coordinate_y, coordinate_z);

        assertClassInvariants();
        throw new NoSuchMethodError("Methode worked fine but no Returntype is declared and able to be declared. This method is not supported in Value Object Representation of CartesianCoordinate");

    }


    @Override
    public String getIdAsString() {
        return null;
    }


    /**
     * Part of Coordinate Interface
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        return this;
    }

    /**
     * Part of Coordinate Interface
     * @return Physical Representation of SphericCoordinate
     */
    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        CartesianCoordinate origin = getOrCreateCartesianCoordinate(0.0, 0.0, 0.0);
        double r = origin.doGetDistance(this);
        if(Math.abs(r - 0.0) <= (1 / Math.pow(10, SCALE)) ){
            return SphericCoordinate.getOrCreateSphericCoordinate();// (equals 0,0,0) since / 0 is not allowed
        }
        double phi = Math.atan2(this.getY(), this.getX());
        double theta = Math.acos(this.getZ() / r);
        SphericCoordinate sphericCoordinate = SphericCoordinate.getOrCreateSphericCoordinate(r, theta, phi);

        assertIsNotNull(sphericCoordinate);
        assertClassInvariants();
        return sphericCoordinate;
    }

    protected double doGetDistance(CartesianCoordinate cartesianCoordinate){
        assertClassInvariants();
        assertIsNotNull(cartesianCoordinate);

        double x_vec = cartesianCoordinate.getX() - this.getX();
        double y_vec = cartesianCoordinate.getY() - this.getY();
        double z_vec = cartesianCoordinate.getZ() - this.getZ();

        double distance = Math.sqrt((x_vec * x_vec) + (y_vec * y_vec) + (z_vec * z_vec));
        assertClassInvariants();
        asserIsValidDouble(distance);
        return distance;
    }

    public boolean doIsEqual(CartesianCoordinate other_cartesianCoordinate){
        assertClassInvariants();
        assertIsNotNull(other_cartesianCoordinate);
        //carefully compares double coordinates since double is not exakt
        BigDecimal t_x = (new BigDecimal(this.getX())).setScale(SCALE, RoundingMode.DOWN);
        BigDecimal c_x = (new BigDecimal(other_cartesianCoordinate.getX())).setScale(SCALE, RoundingMode.DOWN);
        BigDecimal t_y = (new BigDecimal(this.getY())).setScale(SCALE, RoundingMode.DOWN);
        BigDecimal c_y = (new BigDecimal(other_cartesianCoordinate.getY())).setScale(SCALE, RoundingMode.DOWN);
        BigDecimal t_z = (new BigDecimal(this.getZ())).setScale(SCALE, RoundingMode.DOWN);
        BigDecimal c_z = (new BigDecimal(other_cartesianCoordinate.getZ())).setScale(SCALE, RoundingMode.DOWN);

        assertClassInvariants();

        return t_x.compareTo(c_x) == 0 && t_y.compareTo(c_y) == 0 && t_z.compareTo(c_z) == 0;
    }



    @Override
    public int hashCode() {
        assertClassInvariants();
        return Objects.hash(this.getX(), this.getY(), this.getZ());
    }



    //Asseretions

    /**
     * @throws UncheckedCoordinateException
     *
     */
    private void assertClassInvariants() {
        if(Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isNaN(this.z)){
            throw new UncheckedCoordinateException("CartesianCoordinate hast to have x, y, z, which need to be double Numbers");
        }
    }

    /**
     * @throws UncheckedCoordinateException
     */
    private void asserIsValidDouble(double d){
        if(Double.isNaN(d)){
            throw new UncheckedCoordinateException("Double Value calculated was NaN");
        }
    }


    private void assertIsNotNull(Object obj){
        if(obj == null){
            throw new IllegalArgumentException("argument was null");
        }
    }
}
