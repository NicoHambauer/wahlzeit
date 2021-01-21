package org.wahlzeit.model;

import java.util.HashSet;
import java.util.Objects;

public class RealEstateType {

    RealEstateManager manager;
    RealEstateType superType;
    String typeName;
    HashSet<RealEstateType> subtypes;

    /* Hierarchy

        supertype
        //          \\
     this.typeName   ...
     //  \\         .....
    sub. sub.      ........

     */

    public RealEstateType(RealEstateManager manager, String typeName, RealEstateType superType, RealEstateType[] subtypes){
        this.manager = manager;
        this.typeName = typeName;
        this.superType = superType;
        for (RealEstateType subtype: subtypes) {
            this.subtypes.add(subtype);
        }
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
}
