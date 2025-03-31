package com.roniantonius.kritikumkm.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RentangWaktuDto {
	@NotBlank(message = "Waktu buka is required")
	@Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$")
	private String waktuBuka;	
	
	@NotBlank(message = "Waktu tutup is required")
	@Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$")
	private String waktuTutup;
}
