package com.roniantonius.kritikumkm.domain.dtos;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmkmDto {
	private String id;
	
	private String nama;
	
	private String tipeKonsumsi;
	
	private String informasiKontak;
	
	private Float rataRating;
	
	private GeoPointDto geoLocation;
	
	private AlamatDto alamat;
	
	private JamOperasiDto jamOperasi;
	
	private List<GambarDto> gambars = new ArrayList<>();
	
	private List<KritikDto> kritiks = new ArrayList<>();
	
	private UserDto dibuatOleh;
}
