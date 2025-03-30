package com.roniantonius.kritikumkm.controllers;

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
}
