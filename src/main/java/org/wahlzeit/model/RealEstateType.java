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
import java.util.Iterator;
import java.util.Objects;

public class RealEstateType {

    RealEstateManager manager;

    RealEstateType superType;
    HashSet<RealEstateType> subtypes = new HashSet<>();

    /**
     * e.g. commercial-building or private-building or land or industrial
     */
    String typeName;


    /**
     * use-case: e.g. production, service, store, houshold, non profit
     */
    String make;
    /**
     * e.g. apartment, villa, maisonette, penthouse, loft, terace-villa, shop, tinyshop, conceptStore,
     */
    String model;

    /* Hierarchy

        supertype
        //          \\
     this.typeName   ...
     //  \\         .....
    sub. sub.      ........

     */

    public RealEstateType(String typeName, RealEstateManager manager , RealEstateType superType, RealEstateType[] subtypes){
        assertNotNull(typeName);
        this.manager = manager;
        this.typeName = typeName;
        this.superType = superType;
        if(subtypes != null){
            for (RealEstateType subtype: subtypes) {
                this.subtypes.add(subtype);
            }
        }
    }

    public RealEstateType(String typeName, RealEstateManager manager){
        assertNotNull(typeName);
        this.manager = manager;
        this.typeName = typeName;
    }

    public RealEstate createInstance(HashSet<RealEstatePhoto> realEstatePhotos, RealEstateManager manager){
        return new RealEstate(this, realEstatePhotos, manager);
    }

    public void addSubType(RealEstateType type) {
        assertNotNull(type);
        if (type == null) throw new IllegalArgumentException("Argument was null");
        type.setSuperType(this);
        subtypes.add(type);
    }

    public boolean isSubtype(RealEstateType otherType){
        assertNotNull(otherType);
        if(this.superType == null){
            //base case
            return false;
        } else if(this.superType.equals(otherType)){
            //without rolling back recursion this is the second base case and its not this.equals(otherType) !
            return true;
        }

        return this.superType.isSubtype(otherType);
    }

    public Iterator<RealEstateType> getSubTypeIterator() {
        return subtypes.iterator();
    }

    public boolean hasInstance(RealEstate realEstate) {
        assertNotNull(realEstate);
        if (realEstate.getType() == this) {
            return true;
        }

        for (RealEstateType type : this.subtypes) {
            if (type.hasInstance(realEstate)) {
                return true;
            }
        }
        return false;
    }



    public RealEstateType getSuperType() {
        return superType;
    }

    public void setSuperType(RealEstateType superType) {
        assertNotNull(superType);
        this.superType = superType;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        assertNotNull(make);
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealEstateType)) return false;
        RealEstateType that = (RealEstateType) o;
        return typeName.equals(that.typeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeName);
    }

    private void assertNotNull(Object o){
        if(o == null) throw new IllegalArgumentException("Argument was Null");
    }
}
