package com.roniantonius.kritikumkm.domain.dtos;

import java.util.List;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KritikCreateUpdateRequestDto {
	
	@NotBlank(message = "Isi dari kritik harus memiliki konten")
	private String konten;
	
	@NotBlank(message = "Rating umkm diperlukan")
	@Min(value = 1, message = "Rating harus dalam rentang 1 dan 5")
	@Max(value = 5, message = "Rating harus dalam rentang 1 dan 5")
	private Integer rating;
	
	private List<String> gambarIds;
}
