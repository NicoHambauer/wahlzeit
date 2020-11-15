package org.wahlzeit.model;

public class Location {

    public Coordinate coordinate;

    public Location(){
        this.coordinate = new Coordinate();//standart coordinates (0,0,0) since there is no UI for coordinate input
    }

    public Location(double x, double y, double z){
        this.coordinate = new Coordinate(x, y, z);
    }

    public Coordinate getCoordinate(){
        return this.coordinate;
    }

    @Override
    public boolean equals(Object other_object) {
        //Location extends Object by standart in java
        if(!(other_object instanceof Location) || !(this instanceof Location)) {
            return false;
        }
        Location other_location = (Location) other_object;
        return this.coordinate.isEqual(other_location.coordinate);
    }



}
