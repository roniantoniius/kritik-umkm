package com.roniantonius.kritikumkm.services;

import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.kritikumkm.domain.entities.Gambar;

public interface GambarService {
	Gambar uploadGambar(MultipartFile file);
	Optional<Resource> getGambarAsResource(String id);
}