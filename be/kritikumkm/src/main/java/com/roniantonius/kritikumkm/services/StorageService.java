package com.roniantonius.kritikumkm.services;

import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	String store(MultipartFile file, String namaFile);
	Optional<Resource> loadAsResource(String namaFile);
}
