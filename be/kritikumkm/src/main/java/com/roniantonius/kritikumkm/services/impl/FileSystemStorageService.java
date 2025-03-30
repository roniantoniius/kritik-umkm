package com.roniantonius.kritikumkm.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.kritikumkm.exceptions.StorageException;
import com.roniantonius.kritikumkm.services.StorageService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

	@Value("${app.storage.location:uploads}") // artinya file akan disimpan pada 'app.storage.location' atau folder root '/uploads'
	private String lokasiPenyimpanan;
	private Path ruteFile;
	
	@PostConstruct // method akan dijlankan setelah class diconstruct
	public void init() {
		ruteFile = Paths.get(lokasiPenyimpanan);
		try {
			Files.createDirectories(ruteFile);
		} catch (IOException e) {
			// TODO: handle exception
			throw new StorageException("Tidak bisa inisiasi lokasi penyimpanan file", e);
		}
	}
	
	// return link atau url menuju file yang distore tersebut
	@Override
	public String store(MultipartFile file, String namaFile) {
		// TODO Auto-generated method stub
		try {
			if(file.isEmpty()) {
				throw new StorageException("File yang dimasukkan tidak boleh kosong!");
			}
			
			String formatFile = StringUtils.getFilenameExtension(file.getOriginalFilename());
			String namaFileFix = namaFile + "." + formatFile;
			
			// ambil Path representation tentang dimana harus file disimpan
			Path destinasiFilePath = ruteFile
					.resolve(Paths.get(namaFileFix))
					.normalize()
					.toAbsolutePath();
			
			// error handling untuk memastikan bahwa file yang baru ada dan dikirim ke destinasi root tempat file disimpan
			if (!destinasiFilePath.getParent().equals(ruteFile.toAbsolutePath())) {
				throw new StorageException("Tidak bisa menyimpan file diluar dari lokasi utama penyimpanan file");
			}
			
			// Menulis (copy) file dari input melalui InputStream ke destinasifilePath
			try (InputStream inputStream = file.getInputStream()){
				Files.copy(inputStream, destinasiFilePath, StandardCopyOption.REPLACE_EXISTING);
			}
			
			return namaFileFix;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new StorageException("Gagal menyimpan file!", e);
		}
	}

	@Override
	public Optional<Resource> loadAsResource(String namaFile) {
		// TODO Auto-generated method stub
		
		try {
			// dari string ke Path object
			Path file = ruteFile.resolve(namaFile);
			
			Resource resource = new UrlResource(file.toUri());
			
			if (resource.exists() || resource.isReadable()) {
				return Optional.of(resource);
			} else {
				return Optional.empty();
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			log.warn("Gagal membaca file untuk mengambil file: %s".formatted(namaFile), e);
			return Optional.empty();
		}
		
		
	}

}
