package com.roniantonius.kritikumkm.services.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.roniantonius.kritikumkm.domain.GeoLocation;
import com.roniantonius.kritikumkm.domain.entities.Alamat;
import com.roniantonius.kritikumkm.services.GeoLocationService;

@Service
public class RandomJakartaGeoLocationServiceImpl implements GeoLocationService{
	private static final float MIN_LATITUDE = -6.15f;
	private static final float MAX_LATITUDE = -6.14f;
	private static final float MIN_LONGITUDE = 106.80f;
	private static final float MAX_LONGITUDE = 106.83f;
	
	@Override
	public GeoLocation geoLocate(Alamat alamat) {
		// TODO Auto-generated method stub
		Random acakRandom = new Random();
		double latitude = MIN_LATITUDE + acakRandom.nextDouble() * (MAX_LATITUDE - MIN_LATITUDE);
		double longitude = MIN_LONGITUDE + acakRandom.nextDouble() * (MAX_LONGITUDE - MIN_LONGITUDE);
		
		return GeoLocation.builder()
				.latitude(latitude)
				.longitude(longitude)
				.build();
	}

}
