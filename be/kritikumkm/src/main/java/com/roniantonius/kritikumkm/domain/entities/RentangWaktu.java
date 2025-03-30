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
public class RentangWaktu {
	@Field(type = FieldType.Keyword)
	private String waktuBuka;
	
	@Field(type = FieldType.Keyword)
	private String waktuTutup;
}
