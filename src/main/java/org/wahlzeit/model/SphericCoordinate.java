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
        this.setPhi(resultSet.getDouble("coordinate_phi"));
        this.setTheta(resultSet.getDouble("coordinate_theta"));
        this.setRadius(resultSet.getDouble("coordinate_radius"));
    }


    @Override
    public void writeOn(ResultSet resultSet) throws SQLException {
        resultSet.updateDouble("coordinate_phi", this.getPhi());
        resultSet.updateDouble("coordinate_theta", this.getTheta());
        resultSet.updateDouble("coordinate_radius", this.getRadius());
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
        double x_sin = Math.sin(this.getTheta());
        double x_cos = Math.cos(this.getPhi());
        double x = this.getRadius() * x_sin * x_cos;
        double y = this.getRadius() * Math.sin(this.getTheta()) * Math.sin(this.getPhi());
        double z = this.getRadius() * Math.cos(this.getTheta());
        return new CartesianCoordinate(x, y, z);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public int hashCode() {
        return (int) (this.getRadius() + this.getTheta() + this.getPhi());
    }

    @Override
    public String getIdAsString() {
        return null;
    }



}
