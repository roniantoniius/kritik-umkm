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
public class JamOperasi {
	@Field(type = FieldType.Nested)
	private RentangWaktu senin;
	
	@Field(type = FieldType.Nested)
	private RentangWaktu selasa;
	
	@Field(type = FieldType.Nested)
	private RentangWaktu rabu;
	
	@Field(type = FieldType.Nested)
	private RentangWaktu kamis;
	
	@Field(type = FieldType.Nested)
	private RentangWaktu jumat;
	
	@Field(type = FieldType.Nested)
	private RentangWaktu sabtu;
	
	@Field(type = FieldType.Nested)
	private RentangWaktu minggu;
}
