package com.roniantonius.kritikumkm.domain.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmkmSummaryDto {
	private String id;
	
	private String nama;
	
	private String tipeKonsumsi;
		
	private Float rataRating;
	
	private Integer totalKritiks;
	
	private AlamatDto alamat;
		
	private List<GambarDto> gambars;
}
