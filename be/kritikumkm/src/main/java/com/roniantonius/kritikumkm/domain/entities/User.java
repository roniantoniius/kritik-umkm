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
public class User {
	
	@Field(type = FieldType.Keyword) // berguna ketika pencarian user secara teliti keseluruhan (memasukkan satu haruf dan langsung mencari)
	private String id;
	
	@Field(type = FieldType.Text) // bisa pencarian tapi secara parsial (akan bisa search setelah memasukkan beberapa huruf)
	private String username;
	
	@Field(type = FieldType.Text)
	private String givenName;
	
	@Field(type = FieldType.Text)
	private String familyName;
}