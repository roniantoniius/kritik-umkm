package com.roniantonius.kritikumkm.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.roniantonius.kritikumkm.domain.UmkmCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.entities.Umkm;

public interface UmkmService {
	Umkm createUmkm(UmkmCreateUpdateRequest request);
	
	// serching for umkm
	Page<Umkm> searchUmkms(
			String query,
			Float minRating,
			Float latitude,
			Float longitude,
			Float radiusKm,
			Pageable pageable
	);
}
