package com.roniantonius.kritikumkm.domain.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GambarDto {
	private String url;
	private LocalDateTime waktuGambarUpload;
}