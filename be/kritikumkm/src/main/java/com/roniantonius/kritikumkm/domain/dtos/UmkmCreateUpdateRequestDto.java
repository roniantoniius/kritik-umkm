package com.roniantonius.kritikumkm.domain.dtos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmkmCreateUpdateRequestDto {
	
	@NotBlank(message = "Nama umkm is required")
	private String nama;
	
	@NotBlank(message = "Tipe konsumsi is required")
	private String tipeKonsumsi;
	
	@NotBlank(message = "Informasi kontak is required")
	private String informasiKontak;
	
	@Valid
	private AlamatDto alamat;
	
	@Valid
	private JamOperasiDto jamOperasi;
	
	@Size(min = 1, message = "Minimal ada {min} gambar total")
	private List<String> gambarIds;
}