package com.roniantonius.kritikumkm.domain.entities;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(indexName = "umkms")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Umkm {
	@Id
	private String id;
	
	@Field(type = FieldType.Text)
	private String nama;
	
	@Field(type = FieldType.Text)
	private String tipeKonsumsi;
	
	@Field(type = FieldType.Keyword)
	private String informasiKontak;
	
	@Field(type = FieldType.Float)
	private Float rataRating;
	
	@GeoPointField
	private GeoPoint geoLocation;
	
	@Field(type = FieldType.Nested)
	private Alamat alamat;
	
	@Field(type = FieldType.Nested)
	private JamOperasi jamOperasi;
	
	@Field(type = FieldType.Nested)
	private List<Gambar> gambars = new ArrayList<>();
	
	@Field(type = FieldType.Nested)
	private List<Kritik> kritiks = new ArrayList<>();
	
	@Field(type = FieldType.Nested)
	private User dibuatOleh;
}