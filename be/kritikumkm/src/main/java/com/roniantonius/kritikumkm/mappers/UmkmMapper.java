package com.roniantonius.kritikumkm.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import com.roniantonius.kritikumkm.domain.UmkmCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.dtos.GeoPointDto;
import com.roniantonius.kritikumkm.domain.dtos.JamOperasiDto;
import com.roniantonius.kritikumkm.domain.dtos.UmkmCreateUpdateRequestDto;
import com.roniantonius.kritikumkm.domain.dtos.UmkmDto;
import com.roniantonius.kritikumkm.domain.dtos.UmkmSummaryDto;
import com.roniantonius.kritikumkm.domain.dtos.UserDto;
import com.roniantonius.kritikumkm.domain.entities.JamOperasi;
import com.roniantonius.kritikumkm.domain.entities.Kritik;
import com.roniantonius.kritikumkm.domain.entities.Umkm;
import com.roniantonius.kritikumkm.domain.entities.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UmkmMapper {
	UmkmCreateUpdateRequest toUmkmCreateUpdateRequest(UmkmCreateUpdateRequestDto dto);
	
	UmkmDto toUmkmDto(Umkm umkm);

	// dua method ini belum mandatory
//	UserDto mapTo(User user);
//	JamOperasiDto mapTo(JamOperasi jamOperasi);
	
	@Mapping(target = "latitude", expression = "java(geoPoint.getLat())")
	@Mapping(target = "longitude", expression = "java(geoPoint.getLon())")
	GeoPointDto toGeoPointDto(GeoPoint geoPoint);
	
	// method for search umkm
	@Mapping(source = "kritiks", target = "totalKritiks", qualifiedByName = "populateTotalKritiks")
	UmkmSummaryDto toSummaryDto(Umkm umkm); // source diambil dari variabel di constructor, dan target dari variabel output
	
	// hitung variabel seperti totalKritik dan rataRating
	@Named("populateTotalKritiks")
	default Integer populateTotalKritiks(List<Kritik> kritiks) {
		return kritiks.size();
	}
}