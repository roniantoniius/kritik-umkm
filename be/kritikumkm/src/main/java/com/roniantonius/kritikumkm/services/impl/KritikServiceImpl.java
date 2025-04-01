package com.roniantonius.kritikumkm.services.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.roniantonius.kritikumkm.domain.KritikCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.entities.Gambar;
import com.roniantonius.kritikumkm.domain.entities.Kritik;
import com.roniantonius.kritikumkm.domain.entities.Umkm;
import com.roniantonius.kritikumkm.domain.entities.User;
import com.roniantonius.kritikumkm.exceptions.KritikNotAllowedException;
import com.roniantonius.kritikumkm.exceptions.UmkmNotFoundException;
import com.roniantonius.kritikumkm.repositories.UmkmRepository;
import com.roniantonius.kritikumkm.services.KritikService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KritikServiceImpl implements KritikService{

	private final UmkmRepository umkmRepository;
	
	@Override public Kritik createKritik(User author, String umkmId, KritikCreateUpdateRequest kritik) {
		// TODO Auto-generated method stub
		 Umkm umkm = getUmkmOrThrow(umkmId);
		 
		 boolean limitUser = umkm.getKritiks().stream().anyMatch(k -> k.getDitulisOleh().getId().equals(author.getId()));
		 if (limitUser) {
			 throw new KritikNotAllowedException("User sudah pernah kritik suatu produk umkm");
		 }
		 LocalDateTime waktuSekarang = LocalDateTime.now();
		 List<Gambar> gambars = kritik.getGambarIds().stream()
				 .map(url -> {
					 return Gambar.builder()
							 .url(url)
							 .waktuGambarUpload(waktuSekarang)
							 .build();
				 }).toList();
		 
		 String kritikId = UUID.randomUUID().toString();
		 
		 Kritik kritikCreate = Kritik.builder()
				 .id(kritikId)
				 .konten(kritik.getKonten())
				 .rating(kritik.getRating())
				 .waktuPosting(waktuSekarang)
				 .editTerakhir(waktuSekarang)
				 .gambars(gambars)
				 .ditulisOleh(author)
				 .build();
		 
		 umkm.getKritiks().add(kritikCreate);
		 
		 updateUmkmRataRating(umkm);
		 
		 Umkm simpanUmkm = umkmRepository.save(umkm);
		 return simpanUmkm.getKritiks().stream()
				 .filter(k -> kritikId.equals(k.getId()))
				 .findFirst()
				 .orElseThrow(() -> new RuntimeException("Error mengambil kritik yang sudah dibuat"));
	}
	
	@Override
	public Page<Kritik> listKritik(String umkmId, Pageable pageable) {
		// TODO Auto-generated method stub
		Umkm umkm = getUmkmOrThrow(umkmId);
		List<Kritik> kritiks = umkm.getKritiks();
		
		Sort urutSort = pageable.getSort();
		
		if(urutSort.isSorted()) {
			Sort.Order order = urutSort.iterator().next();
			String property = order.getProperty();
			boolean isAscending = order.getDirection().isAscending();
			
			Comparator<Kritik> comparator = switch (property) {
				case "waktuPosting" -> Comparator.comparing(Kritik::getWaktuPosting);
				case "rating" -> Comparator.comparing(Kritik::getRating);
				default -> Comparator.comparing(Kritik::getWaktuPosting);
			};
			
			kritiks.sort(isAscending ? comparator : comparator.reversed()); // if isAscending true then sort by `comparator` if not then reversed
		} else {
			kritiks.sort(Comparator.comparing(Kritik::getWaktuPosting).reversed());
		}
		
		// mulai untuk membatasi atau pagination
		int mulai = (int) pageable.getOffset();
		if(mulai >= kritiks.size()) {
			return new PageImpl<>(Collections.emptyList(), pageable, kritiks.size()); // we return nothing if the mulai more than equal count of kritik
		}
		int akhir = Math.min((mulai + pageable.getPageSize()), kritiks.size());
		return new PageImpl<>(kritiks.subList(mulai, akhir), pageable, kritiks.size());
	}

	private Umkm getUmkmOrThrow(String umkmId) {
		return umkmRepository.findById(umkmId)
				.orElseThrow(() -> new UmkmNotFoundException("Umkm dengan di tersebut tidak ada: "+ umkmId));
	}
	
	private void updateUmkmRataRating(Umkm umkm) {
		List<Kritik> kritiks = umkm.getKritiks();
		if (kritiks.isEmpty()) {
			umkm.setRataRating(0.0f);
		} else {
			double rataRataRatingBaru = kritiks.stream()
					.mapToDouble(Kritik::getRating)
					.average()
					.orElse(0.0);
			
			umkm.setRataRating((float) rataRataRatingBaru);
		}
	}
}