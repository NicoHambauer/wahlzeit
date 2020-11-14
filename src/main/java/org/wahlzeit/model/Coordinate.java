package org.wahlzeit.model;

public class Coordinate {

    private double x, y, z;

    /**
     *
     * @methodtype constructor
     */
    public Coordinate(){
        this.x = 0.0;
        this.y = 0.0;
        this.z = 0.0;
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

    public double getDistance(Coordinate coordinate){
        double x_vec = coordinate.x - this.x;
        double y_vec = coordinate.y - this.y;
        double z_vec = coordinate.z - this.z;

        return Math.sqrt((x_vec * x_vec) + (y_vec * y_vec) + (z_vec * z_vec));
    }

    public boolean isEqual(Coordinate coordinate){
        //carefully compares double coordinates since double is not exakt
        int x_diff = (int) Math.floor(Math.abs(coordinate.x - this.x));
        int y_diff = (int) Math.floor(Math.abs(coordinate.y - this.y));
        int z_diff = (int) Math.floor(Math.abs(coordinate.z - this.z));
        //Big decimal??
        return x_diff == 0 && y_diff == 0 && z_diff == 0;
    }


}
