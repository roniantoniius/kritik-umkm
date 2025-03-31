package com.roniantonius.kritikumkm.domain;

import java.util.List;

import com.roniantonius.kritikumkm.domain.entities.Alamat;
import com.roniantonius.kritikumkm.domain.entities.JamOperasi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UmkmCreateUpdateRequest {
	private String nama;
		private String tipeKonsumsi;
	
	private String informasiKontak;
	
	private Alamat alamat;
	
	private JamOperasi jamOperasi;
	
	private List<String> gambarIds;
}