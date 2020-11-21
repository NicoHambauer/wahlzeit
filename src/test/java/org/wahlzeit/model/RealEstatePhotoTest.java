package org.wahlzeit.model;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wahlzeit.services.DataObject;

import static org.junit.Assert.*;

public class RealEstatePhotoTest {

    @BeforeClass
    public static void init() {
        PhotoFactory.initialize();
    }

    @Test
    public void testSuperClass(){
        assertTrue(Coordinate.class.getSuperclass() == DataObject.class);
    }
    @Test
    public void testCreatePhoto() {
        //arrange
        Photo photo = PhotoFactory.getInstance().createPhoto();
        //act
        //assert
        assertNotNull(photo);
    }

    @Test
    public void testClassOfPhoto() {
        //arrange
        Photo photo = PhotoFactory.getInstance().createPhoto();
        //act
        //assert
        assertEquals(RealEstatePhoto.class, photo.getClass());
    }

    @Test
    public void testWriteCount(){
        //arrange
        Photo photo1 = PhotoFactory.getInstance().createPhoto();
        PhotoId id = new PhotoId(10);
        Photo photo2 = PhotoFactory.getInstance().createPhoto(id);
        //act
        //assert
        assertTrue(photo1.isDirty());
        assertTrue(photo2.isDirty());
    }

    @Test
    public void testAttributes() {
        //arrange
        Photo photo = PhotoFactory.getInstance().createPhoto();
        //act
        RealEstatePhoto re_photo = (RealEstatePhoto) photo;
        //assert
        assertEquals(5, re_photo.getRooms());
        assertEquals(2, re_photo.getBathrooms());
        assertEquals(1996, re_photo.getYear_built());
        assertEquals(false, re_photo.isRenovated());
    }

    @Test
    public void testBooleanQuery() {
        //arrange
        Photo photo1 = PhotoFactory.getInstance().createPhoto();
        Photo photo2 = PhotoFactory.getInstance().createPhoto();
        //act
        RealEstatePhoto re_photo1 = (RealEstatePhoto) photo1;
        RealEstatePhoto re_photo2 = (RealEstatePhoto) photo2;
        re_photo2.setYear_built(2010);
        //assert
        assertTrue(re_photo1.isOlderThan(re_photo2));
    }

    @Test
    public void testSetter() {
        //arrange
        Photo photo = PhotoFactory.getInstance().createPhoto();
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
        assertEquals(true, re_photo.isRenovated());
    }
}
