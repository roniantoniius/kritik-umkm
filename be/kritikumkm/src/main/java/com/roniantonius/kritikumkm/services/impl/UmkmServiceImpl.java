package com.roniantonius.kritikumkm.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import com.roniantonius.kritikumkm.domain.GeoLocation;
import com.roniantonius.kritikumkm.domain.UmkmCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.entities.Gambar;
import com.roniantonius.kritikumkm.domain.entities.Umkm;
import com.roniantonius.kritikumkm.repositories.UmkmRepository;
import com.roniantonius.kritikumkm.services.GeoLocationService;
import com.roniantonius.kritikumkm.services.UmkmService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UmkmServiceImpl implements UmkmService{
	private final GeoLocationService geoLocationService;
	private final UmkmRepository umkmRepository;
	
	@Override
	public Umkm createUmkm(UmkmCreateUpdateRequest request) {
		// TODO Auto-generated method stub
		GeoLocation lokasi = geoLocationService.geoLocate(request.getAlamat());
		List<String> daftarGambarUrl = request.getGambarIds();
		List<Gambar> daftarGambars = daftarGambarUrl.stream()
				.map(urlGambar -> Gambar.builder()
						.url(urlGambar)
						.waktuGambarUpload(LocalDateTime.now())
						.build()).toList();
		
		Umkm umkm = Umkm.builder()
				.nama(request.getNama())
				.tipeKonsumsi(request.getTipeKonsumsi())
				.informasiKontak(request.getInformasiKontak())
				.rataRating(0f)
				.geoLocation(new GeoPoint(lokasi.getLatitude(), lokasi.getLongitude()))
				.alamat(request.getAlamat())
				.jamOperasi(request.getJamOperasi())
				.gambars(daftarGambars)
				.build();
		
		return umkmRepository.save(umkm);
	}

	@Override
	public Page<Umkm> searchUmkms(String query, Float minRating, Float latitude, Float longitude, Float radiusKm,
			Pageable pageable) {
		// TODO Auto-generated method stub
		
		// searching only rating is selected
		if (minRating != null && (query == null || query.isEmpty())) {
			return umkmRepository.findByRataRatingGreaterThanEqual(minRating, pageable);
		}
		
		// disini untuk search berdasarkan elastic query di mana mengambil semua data berdasarkan nama atau tipe konsumen
		// maka minRating kita ubah jadi 0
		Float searchMinRating = minRating == null ? 0f : minRating;
		
		if (query != null && !query.trim().isEmpty()) {
			return umkmRepository.findByQueryAndMinRating(query, searchMinRating, pageable);
		}
		
		// search berddasarkan radius terdekat dari lokasi
		if (latitude != null && longitude != null && radiusKm != null) {
			return umkmRepository.findByLocationNear(latitude, longitude, radiusKm, pageable);
		}
		
		// kalau semuanya null kita return semua umkm
		return umkmRepository.findAll(pageable);
	}
}
