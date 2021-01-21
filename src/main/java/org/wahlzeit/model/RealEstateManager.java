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

import java.util.HashMap;
import java.util.HashSet;

public class RealEstateManager {

    HashMap<Integer, RealEstate> realEstateInstances = new HashMap<>();
    HashMap<Integer, RealEstateType> realEstateTypes = new HashMap<>();

    public RealEstateManager(){
        //
    }

    public RealEstate getOrCreateRealEstate(String typeName, RealEstateType superType, RealEstateType[] subtypes, HashSet<RealEstatePhoto> realEstatePhotos, RealEstateManager manager){
        RealEstateType type = getOrCreateRealEstateType(typeName, superType, subtypes);
        RealEstate newRealEstate = type.createInstance(realEstatePhotos, manager);
        realEstateInstances.put(newRealEstate.getID(), newRealEstate);
        return newRealEstate;
    }

    public RealEstate getOrCreateRealEstate(String typeName, HashSet<RealEstatePhoto> realEstatePhotos, RealEstateManager manager){
        RealEstateType type = getOrCreateRealEstateType(typeName);
        RealEstate newRealEstate = type.createInstance(realEstatePhotos, manager);
        realEstateInstances.put(newRealEstate.getID(), newRealEstate);
        return newRealEstate;
    }


    private RealEstateType getOrCreateRealEstateType(String typeName, RealEstateType superType, RealEstateType[] subtypes){
        if(realEstateTypes.containsKey(typeName.hashCode())){
            return realEstateTypes.get(typeName.hashCode());
        }
        RealEstateType newType = new RealEstateType(this, typeName, superType, subtypes);
        realEstateTypes.put(typeName.hashCode(), newType);
        return newType;
    }

    private RealEstateType getOrCreateRealEstateType(String typeName){
        if(realEstateTypes.containsKey(typeName.hashCode())){
            return realEstateTypes.get(typeName.hashCode());
        }
        RealEstateType newType = new RealEstateType(this, typeName);
        realEstateTypes.put(typeName.hashCode(), newType);
        return newType;
    }

}
