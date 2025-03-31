package com.roniantonius.kritikumkm.domain.dtos;

import jakarta.validation.Valid;

public class JamOperasiDto {
	
	@Valid
	private RentangWaktuDto senin;
	
	@Valid
	private RentangWaktuDto selasa;
	
	@Valid
	private RentangWaktuDto rabu;
	
	@Valid
	private RentangWaktuDto kamis;
	
	@Valid
	private RentangWaktuDto jumat;
	
	@Valid
	private RentangWaktuDto sabtu;
	
	@Valid
	private RentangWaktuDto minggu;
}
