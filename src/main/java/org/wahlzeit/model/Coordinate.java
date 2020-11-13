package org.wahlzeit.model;

public class Coordinate {

    private double x;
    private double y;
    private double z;

    /**
     *
     * @methodtype constructor
     */
    public Coordinate(){
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    /**
     *
     * @methodtype constructor
     */
    public Coordinate(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
