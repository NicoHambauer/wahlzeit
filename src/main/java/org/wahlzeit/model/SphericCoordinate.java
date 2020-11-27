package org.wahlzeit.model;

import org.wahlzeit.services.DataObject;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SphericCoordinate extends DataObject implements Coordinate{

    private double radius, theta, phi;

    /**
     * Physical Representation of SphericCoordinate
     * With theta as polar angle and phi as horizontal/"azimut" angle
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
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Definition
     *
     * @methodtype constructor
     */
    public SphericCoordinate(double radius, double theta, double phi){
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
        double x = this.getRadius() * Math.sin(this.getTheta()) * Math.cos(this.getPhi());
        double y = this.getRadius() * Math.sin(this.getTheta()) * Math.sin(this.getPhi());
        double z = this.getRadius() * Math.cos(this.getTheta());
        return new CartesianCoordinate(x, y, z);
    }

    @Override
    public double getCartesianDistance(Coordinate other_Coordinate) {
        CartesianCoordinate this_cartesian_coordinate = this.asCartesianCoordinate();
        CartesianCoordinate other_cartesian_coordinate = other_Coordinate.asCartesianCoordinate();

        return this_cartesian_coordinate.getCartesianDistance(other_cartesian_coordinate);
    }

    @Override
    public SphericCoordinate asSphericCoordinate() {
        return this;
    }

    @Override
    public double getCentralAngle(Coordinate other_Coordinate) {
        SphericCoordinate other_spheric_coordinate = other_Coordinate.asSphericCoordinate();//other_spheric_coordinate

        double bigPhi1 = this.doGetMathPhi(this.getTheta());
        double bigPhi2 = doGetMathPhi(other_spheric_coordinate.getTheta());
        double deltaLamda = doGetMathDeltaLambda(this.getPhi(), other_spheric_coordinate.getPhi());

        double pow1 = Math.pow( cos(bigPhi2) * sin(deltaLamda), 2);
        double pow2 = Math.pow( (cos( bigPhi1) * sin(bigPhi2) - (sin(bigPhi1) * cos(bigPhi2) * cos(deltaLamda))), 2);

        double numerator = sqrt( pow1 + pow2);
        double denominator = (sin(bigPhi1) * sin(bigPhi2)) + (cos(bigPhi1) * cos(bigPhi2) * cos(deltaLamda));

        return Math.atan( numerator / denominator);
    }

    @Override
    public boolean isEqual(Coordinate other_Coordinate) {
        SphericCoordinate other_spheric_coordinate = other_Coordinate.asSphericCoordinate();
        //carefully compares double coordinates since double is not exakt
        BigDecimal t_radius = (new BigDecimal(this.getRadius())).setScale(SCALE);
        BigDecimal other_radius = (new BigDecimal(other_spheric_coordinate.getRadius())).setScale(SCALE);
        BigDecimal t_theta = (new BigDecimal(this.getTheta() % 360.0)).setScale(SCALE);
        BigDecimal other_theta = (new BigDecimal(other_spheric_coordinate.getTheta() % 360.0)).setScale(SCALE);
        BigDecimal t_phi = (new BigDecimal(this.getPhi() % 360.0)).setScale(SCALE);
        BigDecimal other_phi = (new BigDecimal(other_spheric_coordinate.getPhi() % 360.0)).setScale(SCALE);

        return t_radius.compareTo(other_radius) == 0 && t_theta.compareTo(other_theta) == 0 && t_phi.compareTo(other_phi) == 0;
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
    public int hashCode() {
        return (int) (this.getRadius() + this.getTheta() + this.getPhi());
    }

    @Override
    public String getIdAsString() {
        return null;
    }

    /**
     * @return Phi, the mathimaticl complementary of theta or more specific the geographic Latitude
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Andere%20Konventionen
     */
    private double doGetMathPhi(double theta){
        return 90.0 - theta;
    }

    /**
     * @return Lamda the mathimaticl complementary of phi or more specific the geographic Longitude
     * see https://de.wikipedia.org/wiki/Kugelkoordinaten#Andere%20Konventionen
     */
    private double doGetMathDeltaLambda(double phi1, double phi2){
        return Math.abs(phi2 - phi1);
    }

    /**
     * makes code more readable
     */
    private double sin(double a){
        return Math.sin(a);
    }

    /**
     * makes code more readable
     */
    private double cos(double a){
        return Math.cos(a);
    }

    /**
     * makes code more readable
     */
    private double sqrt(double a){
        return Math.sqrt(a);
    }
}
