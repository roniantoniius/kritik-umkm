package com.roniantonius.kritikumkm.domain.entities;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.DateFormat;
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
public class Gambar {
	@Field(type = FieldType.Keyword)
	private String url;
	
	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime waktuGambarUpload;
}
