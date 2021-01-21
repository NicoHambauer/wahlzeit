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

    HashSet<RealEstatePhoto> realEstatePhotos = new HashSet<>();
    RealEstateManager manager;

    protected RealEstateType type = null;

    public RealEstate(RealEstateType type, HashSet<RealEstatePhoto> realEstatePhotos, RealEstateManager manager){
        this.type = type;
        if(realEstatePhotos != null){
            this.realEstatePhotos.addAll(realEstatePhotos);
        }
        this.manager = manager;
    }

    public int getID(){
        return Objects.hash(this.type, this.realEstatePhotos);
    }

    public HashSet<RealEstatePhoto> getRealEstatePhotos() {
        return realEstatePhotos;
    }

    public void addRealEstatePhoto(RealEstatePhoto realEstatePhoto) {
        this.realEstatePhotos = realEstatePhotos;
    }

    public RealEstateType getType() {
        return type;
    }

    public void setType(RealEstateType type) {
        this.type = type;
    }

}
