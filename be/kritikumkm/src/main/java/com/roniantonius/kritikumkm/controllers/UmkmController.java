package com.roniantonius.kritikumkm.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.kritikumkm.domain.UmkmCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.dtos.UmkmCreateUpdateRequestDto;
import com.roniantonius.kritikumkm.domain.dtos.UmkmDto;
import com.roniantonius.kritikumkm.domain.dtos.UmkmSummaryDto;
import com.roniantonius.kritikumkm.domain.entities.Umkm;
import com.roniantonius.kritikumkm.mappers.UmkmMapper;
import com.roniantonius.kritikumkm.services.UmkmService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/restaurants")
@RequiredArgsConstructor
public class UmkmController {
    private final UmkmService umkmService;
    private final UmkmMapper umkmMapper;

    @PostMapping
    public ResponseEntity<UmkmDto> createUmkm(
            @Valid @RequestBody UmkmCreateUpdateRequestDto request
    ) {
        UmkmCreateUpdateRequest umkmCreateUpdateRequest = umkmMapper.toUmkmCreateUpdateRequest(request);
        Umkm umkm = umkmService.createUmkm(umkmCreateUpdateRequest);
        UmkmDto umkmDto = umkmMapper.toUmkmDto(umkm);
        return ResponseEntity.ok(umkmDto);
    }
    
    // endpoint for search
    @GetMapping
    public Page<UmkmSummaryDto> searchUmkms(
    		@RequestParam(required = false) String q,
    		@RequestParam(required = false) Float minRating,
    		@RequestParam(required = false) Float latitude,
    		@RequestParam(required = false) Float longitude,
    		@RequestParam(required = false) Float radius,
    		@RequestParam(defaultValue = "1") int page,
    		@RequestParam(defaultValue = "20") int size
    ){
    	Page<Umkm> umkms = umkmService.searchUmkms(q, minRating, latitude, longitude, radius, PageRequest.of(page, size));
    	return umkms.map(umkmMapper::toSummaryDto);
    };
}