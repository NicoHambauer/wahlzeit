package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RealEstateManagerTest {

    @Before
    public void init(){

    }

    //Not a Unit Test but rather a Component/Integration Test
    @Test
    public void testCreation(){
        RealEstateManager manager = new RealEstateManager();

        RealEstate myComercialRealEstete = manager.getOrCreateRealEstate("commercial", null, manager);
        assertNotNull(myComercialRealEstete);

    }
}
