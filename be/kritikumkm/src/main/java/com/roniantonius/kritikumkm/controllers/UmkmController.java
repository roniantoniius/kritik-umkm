package com.roniantonius.kritikumkm.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping(path = "/api/umkms")
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
    
    @GetMapping(path = "/{umkm_id}")
    public ResponseEntity<UmkmDto> getUmkm(@PathVariable("umkm_id") String umkmId){
		return umkmService.getUmkm(umkmId)
				.map(umkm -> ResponseEntity.ok(umkmMapper.toUmkmDto(umkm)))
				.orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping(path = "/{umkm_id}")
    public ResponseEntity<UmkmDto> updateUmkm(@PathVariable("umkm_id") String umkmId, @Valid @RequestBody UmkmCreateUpdateRequestDto requestDto){
		UmkmCreateUpdateRequest request = umkmMapper.toUmkmCreateUpdateRequest(requestDto);
		Umkm umkm = umkmService.updateUmkm(umkmId, request);
		return ResponseEntity.ok(umkmMapper.toUmkmDto(umkm));
    }
    
    @DeleteMapping(path = "/{umkm_id}")
    public ResponseEntity<Void> deleteUmkm(@PathVariable("umkm_id") String umkmId){
    	umkmService.deleteUmkm(umkmId);
    	return ResponseEntity.noContent().build();
    }
}