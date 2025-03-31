package com.roniantonius.kritikumkm.services;

import com.roniantonius.kritikumkm.domain.GeoLocation;
import com.roniantonius.kritikumkm.domain.entities.Alamat;

public interface GeoLocationService {
	GeoLocation geoLocate(Alamat alamat);
}