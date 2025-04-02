package com.roniantonius.kritikumkm.services.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
		 return getKritikUmkm(kritikId, simpanUmkm)
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

	@Override
	public Optional<Kritik> getKritik(String umkmId, String kritikId) {
		// TODO Auto-generated method stub
		Umkm umkmCari = getUmkmOrThrow(umkmId);
		return getKritikUmkm(kritikId, umkmCari);
	}

	private Optional<Kritik> getKritikUmkm(String kritikId, Umkm umkmCari) {
		return umkmCari.getKritiks()
				.stream()
				.filter(k -> kritikId.equals(k.getId()))
				.findFirst();
	}

	@Override
	public Kritik updateKritik(User author, String umkmId, String kritikId, KritikCreateUpdateRequest request) {
		// TODO Auto-generated method stub
		Umkm umkm = getUmkmOrThrow(umkmId);
		
		// user yang kritik sama dengan user yang ingin update
		Kritik cariKritik = getKritikUmkm(kritikId, umkm).orElseThrow(() -> new KritikNotAllowedException("Kritik tidak ditemukan"));
		if(!kritikId.equals(cariKritik.getDitulisOleh().getId())) {
			throw new KritikNotAllowedException("Tidak bisa memperbarui kritik yang dimiliki orang lain");
		}
		
		// kritik dari suatu user yang lebih dari seminggu
		if(LocalDateTime.now().isAfter(cariKritik.getWaktuPosting().plusHours(168))) {
			throw new KritikNotAllowedException("Tidak bisa memperbarui kritik satu minggu lalu");
		}
		
		// update kritik pasti berujung ke update umkm
		cariKritik.setKonten(request.getKonten());
		cariKritik.setRating(request.getRating());
		cariKritik.setEditTerakhir(LocalDateTime.now());
		cariKritik.setGambars(request.getGambarIds().stream()
				.map(idGambar -> Gambar.builder()
						.url(idGambar)
						.waktuGambarUpload(LocalDateTime.now())
						.build())
				.toList());
		
		updateUmkmRataRating(umkm);
		
		// menambah kritik yang baru di paling bawah dari list
		List<Kritik> updatedKritiks =  umkm.getKritiks().stream()
				.filter(k -> !kritikId.equals(k.getId()))
				.collect(Collectors.toList());
		
		updatedKritiks.add(cariKritik);
		umkm.setKritiks(updatedKritiks);
		umkmRepository.save(umkm);
		return cariKritik;
	}

	@Override
	public void deleteKritik(User author, String umkmId, String kritikId) {
		// delete Kritik by ignoring it
		Umkm cariUmkm = getUmkmOrThrow(umkmId);
		
		// user yang kritik sama dengan user yang ingin update
		Kritik cariKritik = getKritikUmkm(kritikId, cariUmkm).orElseThrow(() -> new KritikNotAllowedException("Kritik tidak ditemukan"));
		if(!kritikId.equals(cariKritik.getDitulisOleh().getId())) {
			throw new KritikNotAllowedException("Tidak bisa menghapus kritik yang dimiliki orang lain");
		}
		
		List<Kritik> daftarKritiks = cariUmkm.getKritiks().stream()
				.filter(k -> !kritikId.equals(k.getId()))
				.toList();
		cariUmkm.setKritiks(daftarKritiks);
		updateUmkmRataRating(cariUmkm);
		umkmRepository.save(cariUmkm);
	}
}