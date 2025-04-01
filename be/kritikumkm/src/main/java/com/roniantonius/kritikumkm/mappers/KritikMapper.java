package com.roniantonius.kritikumkm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.roniantonius.kritikumkm.domain.KritikCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.UmkmCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.dtos.KritikCreateUpdateRequestDto;
import com.roniantonius.kritikumkm.domain.dtos.KritikDto;
import com.roniantonius.kritikumkm.domain.dtos.UmkmCreateUpdateRequestDto;
import com.roniantonius.kritikumkm.domain.entities.Kritik;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface KritikMapper {
	KritikCreateUpdateRequest toKritikCreateUpdateRequest(KritikCreateUpdateRequestDto dto);
	KritikDto toDto(Kritik kritik);
}
