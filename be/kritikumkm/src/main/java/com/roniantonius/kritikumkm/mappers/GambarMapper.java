package com.roniantonius.kritikumkm.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.roniantonius.kritikumkm.domain.dtos.GambarDto;
import com.roniantonius.kritikumkm.domain.entities.Gambar;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GambarMapper {
	GambarDto toDto(Gambar gambar);
}