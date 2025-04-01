package com.roniantonius.kritikumkm.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roniantonius.kritikumkm.domain.KritikCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.dtos.KritikCreateUpdateRequestDto;
import com.roniantonius.kritikumkm.domain.dtos.KritikDto;
import com.roniantonius.kritikumkm.domain.entities.Kritik;
import com.roniantonius.kritikumkm.domain.entities.User;
import com.roniantonius.kritikumkm.mappers.KritikMapper;
import com.roniantonius.kritikumkm.services.KritikService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/restaurants/{restaurantId}/reviews")
@RequiredArgsConstructor
public class KritikController {
	
	private final KritikMapper kritikMapper;
	private final KritikService kritikService;
	
	@PostMapping
	public ResponseEntity<KritikDto> createKritik(
			@PathVariable String umkmId,
			@Valid @RequestBody KritikCreateUpdateRequestDto kritikRequestDto,
			@AuthenticationPrincipal Jwt jwt
	){
		KritikCreateUpdateRequest request = kritikMapper.toKritikCreateUpdateRequest(kritikRequestDto);
		User user = jwtToUser(jwt);
		Kritik kritik = kritikService.createKritik(user, umkmId, request);
		return ResponseEntity.ok(kritikMapper.toDto(kritik));
	}
	
	@GetMapping
	public Page<KritikDto> listKritik(
			@PathVariable String umkmId,
			@PageableDefault(
					size = 20,
					page = 0,
					sort = "waktuPosting",
					direction = Sort.Direction.DESC
			) Pageable pageable
	){
		return kritikService.listKritik(umkmId, pageable).map(kritikMapper::toDto);
	}
	
	private User jwtToUser(Jwt jwt) {
		return User.builder()
				.id(jwt.getSubject())
				.username(jwt.getClaimAsString("preferred_username"))
				.givenName(jwt.getClaimAsString("given_name"))
				.familyName(jwt.getClaimAsString("family_name"))
				.build();
	}
}
