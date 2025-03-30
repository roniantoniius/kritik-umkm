package com.roniantonius.kritikumkm.domain.entities;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Alamat {
	
	@Field(type = FieldType.Keyword)
	private String nomorJalan;
	
	@Field(type = FieldType.Text)
	private String namaJalan;
	
	@Field(type = FieldType.Keyword)
	private String perum;
	
	@Field(type = FieldType.Keyword)
	private String kota;
	
	@Field(type = FieldType.Keyword)
	private String kabupaten;
	
	@Field(type = FieldType.Keyword)
	private String negara;
	
	@Field(type = FieldType.Text)
	private String kodePos;
}
