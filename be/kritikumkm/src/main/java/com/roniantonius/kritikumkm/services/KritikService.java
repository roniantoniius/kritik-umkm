package com.roniantonius.kritikumkm.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.roniantonius.kritikumkm.domain.KritikCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.entities.Kritik;
import com.roniantonius.kritikumkm.domain.entities.User;

public interface KritikService {
	Kritik createKritik(User author, String umkmId, KritikCreateUpdateRequest kritik);
	Page<Kritik> listKritik(String umkmId, Pageable pageable);
	Optional<Kritik> getKritik(String umkmId, String kritikId);
	Kritik updateKritik(User author, String umkmId, String kritikId, KritikCreateUpdateRequest request);
	void deleteKritik(User author, String umkmId, String kritikId);
}