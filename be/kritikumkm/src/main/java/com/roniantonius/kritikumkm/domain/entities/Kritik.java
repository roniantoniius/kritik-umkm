package com.roniantonius.kritikumkm.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Kritik {
	@Field(type = FieldType.Keyword)
	private String id;
	
	@Field(type = FieldType.Text)
	private String konten;
	
	@Field(type = FieldType.Integer) // jangan lupa business logic antara 1 - 5
	private Integer rating;
	
	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime waktuPosting;
	
	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime editTerakhir;
	
	@Field(type = FieldType.Nested)
	private List<Gambar> gambars = new ArrayList<>();
	
	@Field(type = FieldType.Nested)
	private User ditulisOleh;
}
