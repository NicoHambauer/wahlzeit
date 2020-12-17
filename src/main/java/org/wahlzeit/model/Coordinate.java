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

public interface Coordinate {

    final int SCALE = 6; //compares Coordinates by amount of SCALE positions after decimal point

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
