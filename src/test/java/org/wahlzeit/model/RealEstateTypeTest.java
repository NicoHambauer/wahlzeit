package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RealEstateTypeTest {

    @Before //each
    public void init() {

    }

    @Test
    public void testSubtype(){
        //arrange
        RealEstateType commercial = new RealEstateType("commercial", null);
        RealEstateType privateProp = new RealEstateType("private", null);

        RealEstateType commericalProduction = new RealEstateType("production", null);
        RealEstateType commercialStore = new RealEstateType("store", null);

        RealEstateType commercialStoreConceptStore = new RealEstateType("ConceptStore", null);

        //act
        commercial.addSubType(commericalProduction);
        commercial.addSubType(commercialStore);

        commercialStore.addSubType(commercialStoreConceptStore);

        //assert
        assertTrue(commercialStore.isSubtype(commercial));
        assertTrue(commercialStoreConceptStore.isSubtype(commercial));

        /* Hierarchy

            commercial
            //          \\
         store   ...
        //     \\         .....
       ConcStore ...      ........

        */
    }
}
