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

import org.wahlzeit.utils.CheckedCoordinateException;

public interface Coordinate {

    final int SCALE = 6; //compares Coordinates by amount of SCALE positions after decimal point

    /**
     *
     * @methodtype Query Method Interpretation Method
     * @return a Coordinate as an CartesianCoordinate
     */
    public CartesianCoordinate asCartesianCoordinate() throws CheckedCoordinateException;

    /**
     *
     * @methodtype Query Method Interpretation Method
     * @return a Coordinate as an SphericCoordinate
     */
    public SphericCoordinate asSphericCoordinate() throws CheckedCoordinateException;

    /**
     *
     * @param other_Coordinate
     * @return cartesian Distance from  this coordinate to other coordinate
     */
    public double getCartesianDistance(Coordinate other_Coordinate) throws CheckedCoordinateException;


    /**
     *
     * @param other_Coordinate
     * @return central angle in range 0 and 2*PI from this coordinate to other coordinate
     */
    public double getCentralAngle(Coordinate other_Coordinate) throws CheckedCoordinateException;

    /**
     * @methodtype query comparison
     * @param other_Coordinate
     * @return true if this coordinate is equal to other coordinate
     */
    public boolean isEqual(Coordinate other_Coordinate) throws CheckedCoordinateException;

}
