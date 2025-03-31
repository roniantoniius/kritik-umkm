package com.roniantonius.kritikumkm.domain.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.roniantonius.kritikumkm.domain.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KritikDto {
	private String id;
	
	private String konten;
	
	private Integer rating;
	
	private LocalDateTime waktuPosting;
	
	private LocalDateTime editTerakhir;
	
	private List<GambarDto> gambars = new ArrayList<>();
	
	private User ditulisOleh;
}
