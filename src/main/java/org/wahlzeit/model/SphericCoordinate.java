package org.wahlzeit.model;

public class SphericCoordinate implements Coordinate{

    private double radius, theta, phi;

    /**
     *
     * @methodtype constructor
     */
    public SphericCoordinate(){
        this.radius = 0;
        this.theta = 90.0;
        this.phi = 0;
    }

    /**
     *
     * @methodtype constructor
     */
    public SphericCoordinate(double radius, double theta, double phi){
        this.radius = radius;
        this.theta = theta;
        this.phi = phi;
    }

    @Override
    public CartesianCoordinate asCartesianCoordinate() {
        return null;
    }

    @Override
    public double getCartesianDistance(Coordinate other_Coordinate) {
        return 0;
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return null;
    }

    @Override
    public double getCentralAngle(Coordinate other_Coordinate) {
        return 0;
    }

    @Override
    public boolean isEqual(Coordinate other_Coordinate) {
        return false;
    }
}
