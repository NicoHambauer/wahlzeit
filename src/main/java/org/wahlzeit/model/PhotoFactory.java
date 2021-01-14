/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
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

import java.sql.*;

import org.wahlzeit.services.*;
import org.wahlzeit.utils.DesignPatternInstance;

/**
 * An Abstract Factory for creating photos and related objects.
 */
@DesignPatternInstance(
		purpose = "Creational",
		scope = "Object",
		patternName = "Abtract Factory",
		patternParticipants = {"Abstract Factory", "Concrete Factory", "Abstract Product", "Concrete Product"},
		instanceParticipants = {"PhotoFactory", "RealEstatePhotoFactory", "Photo", "RealEstatePhoto"},
		roleOfAnnotatedClass = "Abstract Factory"
)
public class PhotoFactory {
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	@DesignPatternInstance(
			purpose = "Creational",
			scope = "Object",
			patternName = "Singleton",
			patternParticipants = {"Singleton"},
			instanceParticipants = {"PhotoFactory"},
			roleOfAnnotatedClass = "Singleton"
	)
	private static PhotoFactory instance = null;
	
	/**
	 * Public singleton access method.
	 */
	public static synchronized PhotoFactory getInstance() {
		if (instance == null) {
			SysLog.logSysInfo("setting specific RealEstatePhotoFactory");
			setInstance(new RealEstatePhotoFactory());
		}
		
		return instance;
	}
	
	/**
	 * Method to set the singleton instance of PhotoFactory.
	 */
	protected static synchronized void setInstance(PhotoFactory photoFactory) {
		if (instance != null) {
			throw new IllegalStateException("attempt to initialize PhotoFactory twice");
		}
		
		instance = photoFactory;
	}
	
	/**
	 * Hidden singleton instance; needs to be initialized from the outside.
	 */
	public static void initialize() {
		getInstance(); // drops result due to getInstance() side-effects
	}
	
	/**
	 * 
	 */
	protected PhotoFactory() {
		// do nothing
	}

	/**
	 * @methodtype factory
	 */
	public Photo createPhoto() {
		return new Photo();//does not need to be changed to RealEstatePhotoFactory since the method is overwritten
	}
	
	/**
	 * 
	 */
	@DesignPatternInstance(
			purpose = "Creational",
			scope = "Class",
			patternName = "Factory Method",
			patternParticipants = { "Creator", "Concrete Creator", "Product", "Concrete Product"},
			instanceParticipants = { "PhotoFactory", "RealEstatePhotoFactory", "Photo", "RealEstatePhoto"},
			roleOfAnnotatedClass = "Creator"
	)
	public Photo createPhoto(PhotoId id) {
		return new Photo(id);
	}
	
	/**
	 * 
	 */
	@DesignPatternInstance(
			purpose = "Creational",
			scope = "Class",
			patternName = "Factory Method",
			patternParticipants = { "Creator", "Concrete Creator", "Product", "Concrete Product"},
			instanceParticipants = { "PhotoFactory", "RealEstatePhotoFactory", "Photo", "RealEstatePhoto"},
			roleOfAnnotatedClass = "Creator"
	)
	public Photo createPhoto(ResultSet rs) throws SQLException {
		return new Photo(rs);
	}
	
	/**
	 * 
	 */
	public PhotoFilter createPhotoFilter() {
		return new PhotoFilter();
	}
	
	/**
	 * 
	 */
	public PhotoTagCollector createPhotoTagCollector() {
		return new PhotoTagCollector();
	}

}
