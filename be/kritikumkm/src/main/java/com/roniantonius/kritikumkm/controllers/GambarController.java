package com.roniantonius.kritikumkm.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.kritikumkm.domain.dtos.GambarDto;
import com.roniantonius.kritikumkm.domain.entities.Gambar;
import com.roniantonius.kritikumkm.mappers.GambarMapper;
import com.roniantonius.kritikumkm.services.GambarService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/photos")
public class GambarController {
	private final GambarService gambarService;
	private final GambarMapper gambarMapper;
	
	@PostMapping
	public GambarDto uploadGambar(@RequestParam("file") MultipartFile file) {
		Gambar gambarTersimpan = gambarService.uploadGambar(file);
		return gambarMapper.toDto(gambarTersimpan);
	}
	
	// display the Gambar/Image correctly
	@GetMapping(path = "/{id:.+}")
	public ResponseEntity<Resource> getGambar(@PathVariable String id){
		return gambarService.getGambarAsResource(id).map(gambar ->
			ResponseEntity.ok()
				.contentType(
						MediaTypeFactory.getMediaType(gambar).orElse(MediaType.APPLICATION_OCTET_STREAM)
				)
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline") // kita pengen gambar langsung ditampilkan jangan didownload dulu oleh browser
				.body(gambar)
		).orElse(ResponseEntity.notFound().build());
	}
}
