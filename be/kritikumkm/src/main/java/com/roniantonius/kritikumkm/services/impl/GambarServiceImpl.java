package com.roniantonius.kritikumkm.services.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.kritikumkm.domain.entities.Gambar;
import com.roniantonius.kritikumkm.services.GambarService;
import com.roniantonius.kritikumkm.services.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GambarServiceImpl implements GambarService {
	
	private final StorageService storageService;
	
	@Override public Gambar uploadGambar(MultipartFile file) {
		// TODO Auto-generated method stub
		String idGambar = UUID.randomUUID().toString();
		String urlGambar = storageService.store(file, idGambar);
		
		return Gambar.builder()
				.url(urlGambar)
				.waktuGambarUpload(LocalDateTime.now())
				.build();
	}

	@Override public Optional<Resource> getGambarAsResource(String id) {
		// TODO Auto-generated method stub
		return storageService.loadAsResource(id);
	}

}
