package org.wahlzeit.model;

import java.math.BigDecimal;

public class Coordinate {

    private double x, y, z;
    private final int SCALE = 6; //compares Coordinates by amount of SCALE positions after decimal point

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
        BigDecimal t_x = (new BigDecimal(this.x)).setScale(SCALE);
        BigDecimal c_x = (new BigDecimal(coordinate.x)).setScale(SCALE);
        BigDecimal t_y = (new BigDecimal(this.y)).setScale(SCALE);
        BigDecimal c_y = (new BigDecimal(coordinate.y)).setScale(SCALE);
        BigDecimal t_z = (new BigDecimal(this.z)).setScale(SCALE);
        BigDecimal c_z = (new BigDecimal(coordinate.z)).setScale(SCALE);

        return t_x.compareTo(c_x) == 0 && t_y.compareTo(c_y) == 0 && t_z.compareTo(c_z) == 0;
    }


}
