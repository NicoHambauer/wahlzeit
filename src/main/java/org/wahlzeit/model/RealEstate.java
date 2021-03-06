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

import java.util.HashSet;
import java.util.Objects;

public class RealEstate {

    //Collaboration : RealEstate (Element) --- RealEstateManager (Manager)
    RealEstateManager manager;

    //Collaboration : RealEstatePhoto (Client) --- RealEstate (Service)
    HashSet<RealEstatePhoto> realEstatePhotos = new HashSet<>();

    //Collaboration : RealEstate (Base Object) --- RealEstateType (TypeObject)
    protected RealEstateType type = null;

    public RealEstate(RealEstateType type, HashSet<RealEstatePhoto> realEstatePhotos, RealEstateManager manager){
        this.type = type;
        if(realEstatePhotos != null){
            this.realEstatePhotos.addAll(realEstatePhotos);
        }
        this.manager = manager;
        //Object Creation: This Constructor is called from RealEstateType#createInstance()
        //					So Instantiation Process is as follows: RealEstateType#createInstance() --> RealEstate#RealEstate()
        //					(Design) Patterns of Object Creation: "Factory Method/(Abstract) Factory" ( RealEstateType#createInstance() {... return new RealEstate(..);}
        //                  Base level (0): Here Object RealEstate is created by constructor (Step 3)
    }

    public int getID(){
        return Objects.hash(this.type, this.realEstatePhotos);
    }

    /**
     * Collaboration : RealEstatePhoto (Client) --- RealEstate (Service)
     */
    public HashSet<RealEstatePhoto> getRealEstatePhotos() {
        return realEstatePhotos;
    }

    /**
     * Collaboration : RealEstatePhoto (Client) --- RealEstate (Service)
     *
     */
    public void addRealEstatePhoto(RealEstatePhoto realEstatePhoto) {
        this.realEstatePhotos.add(realEstatePhoto);
    }

    /**
     * Collaboration : RealEstate (Base Object) --- RealEstateType (TypeObject)
     * @return
     */
    public RealEstateType getType() {
        return type;
    }

    /**
     * Collaboration : RealEstate (Base Object) --- RealEstateType (TypeObject)
     * @param type
     */
    public void setType(RealEstateType type) {
        this.type = type;
    }

}
