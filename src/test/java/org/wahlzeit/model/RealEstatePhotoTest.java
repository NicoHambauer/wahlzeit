package org.wahlzeit.model;

import org.junit.Before;
import org.junit.Test;
import org.wahlzeit.services.DataObject;

import static org.junit.Assert.*;

public class RealEstatePhotoTest {

    private Photo photo;

    @Before //each
    public void init() {
        this.photo = new RealEstatePhoto();
    }

    @Test
    public void testSuperClass(){
        assertTrue(RealEstatePhoto.class.getSuperclass() == Photo.class);
    }
    @Test
    public void testCreatePhoto() {
        //arrange see init
        //act
        //assert
        assertNotNull(photo);
    }

    @Test
    public void testClassOfPhoto() {
        //arrange see init
        //act
        //assert
        assertEquals(RealEstatePhoto.class, photo.getClass());
    }

    @Test
    public void testWriteCount(){
        //arrange
        Photo photo1 = photo; //see init
        PhotoId id = new PhotoId(10);
        Photo photo2 = new RealEstatePhoto(id);
        //act
        //assert
        assertTrue(photo1.isDirty());
        assertTrue(photo2.isDirty());
    }

    @Test
    public void testAttributes() {
        //arrange see init
        //act
        RealEstatePhoto re_photo = (RealEstatePhoto) photo;
        //assert
        assertEquals(5, re_photo.getRooms());
        assertEquals(2, re_photo.getBathrooms());
        assertEquals(1996, re_photo.getYear_built());
        assertFalse(re_photo.isRenovated());
    }

    @Test
    public void testBooleanQuery() {
        //arrange
        Photo photo1 = photo;
        Photo photo2 = new RealEstatePhoto();
        //act
        RealEstatePhoto re_photo1 = (RealEstatePhoto) photo1;
        RealEstatePhoto re_photo2 = (RealEstatePhoto) photo2;
        re_photo2.setYear_built(2010);
        //assert
        assertTrue(re_photo1.isOlderThan(re_photo2));
    }

    @Test
    public void testSetter() {
        //arrange see init
        //act
        RealEstatePhoto re_photo = (RealEstatePhoto) photo;
        re_photo.setYear_built(2020);
        re_photo.setBathrooms(10);
        re_photo.setRooms(20);
        re_photo.setRenovated(true);
        //assert
        assertEquals(2020, re_photo.getYear_built());
        assertEquals(10, re_photo.getBathrooms());
        assertEquals(20, re_photo.getRooms());
        assertTrue(re_photo.isRenovated());
    }
}
