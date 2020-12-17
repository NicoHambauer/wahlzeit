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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SphericCoordinate extends AbstractCoordinate{

    private double radius, theta, phi;

    /**
     * Physical Representation of SphericCoordinate
     * With theta as polar angle and phi as horizontal/"azimut" angle
     * in radians
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Definition
     *
     * @methodtype constructor
     */
    public SphericCoordinate(){
        this.radius = 0.0;
        this.theta = 0.0;
        this.phi = 0.0;
    }

    /**
     * Physical Representation of SphericCoordinate
     * With theta as polar angle and phi as horizontal/"azimut" angle
     * in radians
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Definition
     *
     * @methodtype constructor
     */
    public SphericCoordinate(double radius, double theta, double phi){
        if(theta > Math.PI) throw new IllegalArgumentException("Theta must be between 0 and PI");
        if(phi > Math.PI * 2) throw new IllegalArgumentException("Theta must be between 0 and 2 * PI");
        if(radius < 0) throw new IllegalArgumentException("radius must be >= 0");
        this.radius = radius;
        this.theta = theta;
        this.phi = phi;
    }

    /**
     * @methodtype set
     */
    public void setPhi(double phi) {
        this.phi = phi;
    }

    /**
     * @methodtype set
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }

    /**
     * @methodtype set
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * @methodtype get
     */
    public double getPhi() {
        return this.phi;
    }

    /**
     * @methodtype get
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * @methodtype get
     */
    public double getTheta() {
        return this.theta;
    }


    @Override
    public void readFrom(ResultSet resultSet) throws SQLException {
        assertClassInvariants();
        assertIsNotNull(resultSet);
        this.setPhi(resultSet.getDouble("coordinate_phi"));
        this.setTheta(resultSet.getDouble("coordinate_theta"));
        this.setRadius(resultSet.getDouble("coordinate_radius"));
        assertClassInvariants();
    }


    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        assertClassInvariants();
        assertIsNotNull(resultSet);
        resultSet.updateDouble("coordinate_phi", this.getPhi());
        resultSet.updateDouble("coordinate_theta", this.getTheta());
        resultSet.updateDouble("coordinate_radius", this.getRadius());
        assertClassInvariants();
    }

    @Override
    public void writeId(PreparedStatement stmt, int pos) throws SQLException {

    }

    /**
     * Careful: Physical Representation of SphericCoordinate to CartesianCoordinate
     *
     */
    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        assertClassInvariants();
        double x_sin = Math.sin(this.getTheta());
        double x_cos = Math.cos(this.getPhi());
        double x = this.getRadius() * x_sin * x_cos;
        double y = this.getRadius() * Math.sin(this.getTheta()) * Math.sin(this.getPhi());
        double z = this.getRadius() * Math.cos(this.getTheta());
        CartesianCoordinate cartesianCoordinate = new CartesianCoordinate(x, y, z);
        assertIsNotNull(cartesianCoordinate);
        assertClassInvariants();
        return cartesianCoordinate;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        assertClassInvariants();
        return this;
    }

    public double doGetCentralAngle(SphericCoordinate other_spheric_coordinate) {
        assertClassInvariants();
        assertIsNotNull(other_spheric_coordinate);

        double bigPhi1 = doGetMathPhi(this.getTheta());
        double bigPhi2 = doGetMathPhi(other_spheric_coordinate.getTheta());
        double deltaLamda = doGetMathDeltaLambda(this.getPhi(), other_spheric_coordinate.getPhi());

        double pow1 = Math.pow( Math.cos(bigPhi2) * Math.sin(deltaLamda), 2);
        double pow2 = Math.pow( (Math.cos( bigPhi1) * Math.sin(bigPhi2) - (Math.sin(bigPhi1) * Math.cos(bigPhi2) * Math.cos(deltaLamda))), 2);

        double numerator = Math.sqrt( pow1 + pow2);
        double denominator = (Math.sin(bigPhi1) * Math.sin(bigPhi2)) + (Math.cos(bigPhi1) * Math.cos(bigPhi2) * Math.cos(deltaLamda));

        assertClassInvariants();
        return Math.atan( numerator / denominator);
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    /**
     * @return Phi, the mathimaticl complementary of theta or more specific the geographic Latitude
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Andere%20Konventionen
     */
    private double doGetMathPhi(double theta){ return Math.toRadians(90.0) - theta; }

    /**
     * @return Lamda the mathimaticl complementary of phi or more specific the geographic Longitude
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Andere%20Konventionen
     */
    private double doGetMathDeltaLambda(double phi1, double phi2){
        return Math.abs(phi2 - phi1);
    }

    private void assertClassInvariants() {
        if(Double.isNaN(this.phi) || Double.isNaN(this.theta) || Double.isNaN(this.radius)){
            throw new IllegalStateException("CartesianCoordinate hast to have phi, theta, radius which need to be double Numbers");
        }
        if(this.phi > Math.toRadians(360.0) || this.phi < Math.toRadians(0.0) || this.theta > Math.toRadians(180.0) || this.theta < Math.toRadians(0.0) || this.radius < 0.0){
            throw new IllegalStateException("CartesianCoordinate hast to have 0.0 < phi < 2 PI, 0.0 < theta < PI, radius (>0)");
        }
    }

    private void assertIsNotNull(Object obj){
        if(obj == null){
            throw new IllegalArgumentException("argument was null");
        }
    }

}
