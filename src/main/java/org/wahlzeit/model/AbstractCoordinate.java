package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractCoordinate extends DataObject implements Coordinate{

    public abstract CartesianCoordinate asCartesianCoordinate();

    public abstract SphericCoordinate asSphericCoordinate();

    public abstract void readFrom(ResultSet rset) throws SQLException;

    public abstract void  writeOn(ResultSet rset) throws SQLException;

    public abstract void writeId(PreparedStatement stmt, int pos) throws SQLException;

    @Override
    public double getCartesianDistance(Coordinate other_Coordinate) {
        CartesianCoordinate this_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesianCoordinate = other_Coordinate.asCartesianCoordinate();
        return this_coordinate.doGetDistance(other_cartesianCoordinate);
    }

    @Override
    public double getCentralAngle(Coordinate other_Coordinate) {
        SphericCoordinate this_spheric_coordinate = this.asSphericCoordinate();
        SphericCoordinate other_spheric_coordinate = other_Coordinate.asSphericCoordinate();//other_spheric_coordinate
        double bigPhi1 = doGetMathPhi(this_spheric_coordinate.getTheta());
        double bigPhi2 = doGetMathPhi(other_spheric_coordinate.getTheta());
        double deltaLamda = doGetMathDeltaLambda(this_spheric_coordinate.getPhi(), other_spheric_coordinate.getPhi());

        double pow1 = Math.pow( Math.cos(bigPhi2) * Math.sin(deltaLamda), 2);
        double pow2 = Math.pow( (Math.cos( bigPhi1) * Math.sin(bigPhi2) - (Math.sin(bigPhi1) * Math.cos(bigPhi2) * Math.cos(deltaLamda))), 2);

        double numerator = Math.sqrt( pow1 + pow2);
        double denominator = (Math.sin(bigPhi1) * Math.sin(bigPhi2)) + (Math.cos(bigPhi1) * Math.cos(bigPhi2) * Math.cos(deltaLamda));

        return Math.atan( numerator / denominator);
    }

    @Override
    public boolean equals(Object other_object) {
        if(!(Coordinate.class.isAssignableFrom(other_object.getClass())) || !(Coordinate.class.isAssignableFrom(this.getClass())) ) {
            return false;
        }
        Coordinate other_Coordinate = (Coordinate) other_object;
        return this.isEqual(other_Coordinate);
    }

    @Override
    public boolean isEqual(Coordinate other_Coordinate) {
        CartesianCoordinate this_cartesian_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesian_coordinate = other_Coordinate.asCartesianCoordinate();

        return this_cartesian_coordinate.doIsEqual(other_cartesian_coordinate);
    }

    /**
     *
     */
    @Override
    public String getIdAsString() {
        return null;
    }

    /**
     * @return Phi, the mathimaticl complementary of theta or more specific the geographic Latitude
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Andere%20Konventionen
     */
    private double doGetMathPhi(double theta){
        return Math.toRadians(90.0) - theta;
    }

    /**
     * @return Lamda the mathimaticl complementary of phi or more specific the geographic Longitude
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Andere%20Konventionen
     */
    private double doGetMathDeltaLambda(double phi1, double phi2){
        return Math.abs(phi2 - phi1);
    }
}
