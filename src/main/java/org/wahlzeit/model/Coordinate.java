package org.wahlzeit.model;

public interface Coordinate {

    /**
     *
     * @methodtype Query Method Interpretation Method
     * @return a Coordinate as an CartesianCoordinate
     */
    public CartesianCoordinate asCartesianCoordinate();


    public double getCartesianDistance(Coordinate other_Coordinate);

    /**
     *
     * @methodtype Query Method Interpretation Method
     * @return a Coordinate as an SphericCoordinate
     */
    public SphericCoordinate asSphericCoordinate();

    public double getCentralAngle(Coordinate other_Coordinate);

    public boolean isEqual(Coordinate other_Coordinate);


}
