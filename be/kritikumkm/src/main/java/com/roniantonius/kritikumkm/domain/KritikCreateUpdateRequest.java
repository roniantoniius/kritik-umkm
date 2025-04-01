package com.roniantonius.kritikumkm.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KritikCreateUpdateRequest {
	private String konten;
	
	private Integer rating;
	
	private List<String> gambarIds;
}
