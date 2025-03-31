package com.roniantonius.kritikumkm.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlamatDto {

	@NotBlank(message = "Nomor jalan is required")
	@Pattern(regexp = "^[0-9]{1,5}[a-zA-Z]?$", message = "nomor jalan tidak valid")
	private String nomorJalan;
	
	@NotBlank(message = "Nama Jalan is required")
	private String namaJalan;
	
	private String perum;
	
	@NotBlank(message = "Nama Kota is required")
	private String kota;
	
	@NotBlank(message = "Nama Kabupaten is required")
	private String kabupaten;
	
	@NotBlank(message = "Nama Negara is required")
	private String negara;
	
	@NotBlank(message = "Kodepos is required")
	private String kodePos;
}
