package com.roniantonius.kritikumkm.manual;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.multipart.MultipartFile;

import com.roniantonius.kritikumkm.domain.UmkmCreateUpdateRequest;
import com.roniantonius.kritikumkm.domain.entities.Alamat;
import com.roniantonius.kritikumkm.domain.entities.Gambar;
import com.roniantonius.kritikumkm.domain.entities.JamOperasi;
import com.roniantonius.kritikumkm.domain.entities.RentangWaktu;
import com.roniantonius.kritikumkm.services.GambarService;
import com.roniantonius.kritikumkm.services.UmkmService;
import com.roniantonius.kritikumkm.services.impl.RandomJakartaGeoLocationServiceImpl;

@SpringBootTest
public class UmkmDataLoaderTest {
	@Autowired
    private UmkmService umkmService;

    @Autowired
    private RandomJakartaGeoLocationServiceImpl geoLocationService;

    @Autowired
    private GambarService gambarService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    @Rollback(false) // Allow changes to persist
    public void createSampleRestaurants() throws Exception {
        List<UmkmCreateUpdateRequest> umkms = createUmkmData();
        umkms.forEach(restaurant -> {
            String fileName = restaurant.getGambarIds().getFirst();
            Resource resource = resourceLoader.getResource("classpath:testdata/" + fileName);
            MultipartFile multipartFile = null;
            try {
                multipartFile = new MockMultipartFile(
                        "file", // parameter name
                        fileName, // original filename
                        MediaType.IMAGE_PNG_VALUE,
                        resource.getInputStream()
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Call the service method
            Gambar uploadedPhoto = gambarService.uploadGambar(multipartFile);

            restaurant.setGambarIds(List.of(uploadedPhoto.getUrl()));

            umkmService.createUmkm(restaurant);

            System.out.println("Created Umkm: " + restaurant.getNama());
        });
    }

    private List<UmkmCreateUpdateRequest> createUmkmData() {
        return Arrays.asList(
                createUmkm(
                        "The Golden Dragon",
                        "Chinese",
                        "+44 20 7123 4567",
                        createAddress("12", "Gerrard Street", null, "London", "Greater London", "W1D 5PR", "United Kingdom"),
                        createStandardOperatingHours("11:30", "23:00", "11:30", "23:30"),
                        "golden-dragon.png"
                ),
                createUmkm(
                        "La Petite Maison",
                        "French",
                        "+44 20 7234 5678",
                        createAddress("54", "Brook Street", null, "London", "Greater London", "W1K 4HR", "United Kingdom"),
                        createStandardOperatingHours("12:00", "22:30", "12:00", "23:00"),
                        "la-petit-maison.png"
                ),
                createUmkm(
                        "Raj Pavilion",
                        "Indian",
                        "+44 20 7345 6789",
                        createAddress("27", "Brick Lane", null, "London", "Greater London", "E1 6PU", "United Kingdom"),
                        createStandardOperatingHours("12:00", "23:00", "12:00", "23:30"),
                        "raj-pavilion.png"
                ),
                createUmkm(
                        "Sushi Master",
                        "Japanese",
                        "+44 20 7456 7890",
                        createAddress("8", "Poland Street", null, "London", "Greater London", "W1F 8PR", "United Kingdom"),
                        createStandardOperatingHours("11:30", "22:00", "11:30", "22:30"),
                        "sushi-master.png"
                ),
                createUmkm(
                        "The Rustic Olive",
                        "Italian",
                        "+44 20 7567 8901",
                        createAddress("92", "Dean Street", null, "London", "Greater London", "W1D 3SR", "United Kingdom"),
                        createStandardOperatingHours("11:00", "23:00", "11:00", "23:30"),
                        "rustic-olive.png"
                ),
                createUmkm(
                        "El Toro",
                        "Spanish",
                        "+44 20 7678 9012",
                        createAddress("15", "Charlotte Street", null, "London", "Greater London", "W1T 1RH", "United Kingdom"),
                        createStandardOperatingHours("12:00", "23:00", "12:00", "23:30"),
                        "el-toro.png"
                ),
                createUmkm(
                        "The Greek House",
                        "Greek",
                        "+44 20 7789 0123",
                        createAddress("32", "Store Street", null, "London", "Greater London", "WC1E 7BS", "United Kingdom"),
                        createStandardOperatingHours("12:00", "22:30", "12:00", "23:00"),
                        "greek-house.png"
                ),
                createUmkm(
                        "Seoul Kitchen",
                        "Korean",
                        "+44 20 7890 1234",
                        createAddress("71", "St John Street", null, "London", "Greater London", "EC1M 4AN", "United Kingdom"),
                        createStandardOperatingHours("11:30", "22:00", "11:30", "22:30"),
                        "seoul-kitchen.png"
                ),
                createUmkm(
                        "Thai Orchid",
                        "Thai",
                        "+44 20 7901 2345",
                        createAddress("45", "Warren Street", null, "London", "Greater London", "W1T 6AD", "United Kingdom"),
                        createStandardOperatingHours("11:00", "22:30", "11:00", "23:00"),
                        "thai-orchid.png"
                ),
                createUmkm(
                        "The Burger Joint",
                        "American",
                        "+44 20 7012 3456",
                        createAddress("88", "Commercial Street", null, "London", "Greater London", "E1 6LY", "United Kingdom"),
                        createStandardOperatingHours("11:00", "23:00", "11:00", "23:30"),
                        "burger-joint.png"
                )
        );
    }

    private UmkmCreateUpdateRequest createUmkm(
            String nama,
            String tipeKonsumsi,
            String informasiKontak,
            Alamat alamat,
            JamOperasi jamOperasi,
            String gambarIds
    ) {
        return UmkmCreateUpdateRequest.builder()
                .nama(nama)
                .tipeKonsumsi(tipeKonsumsi)
                .informasiKontak(informasiKontak)
                .alamat(alamat)
                .jamOperasi(jamOperasi)
                .gambarIds(List.of(gambarIds))
                .build();
    }

    private Alamat createAddress(
            String nomorJalan,
            String namaJalan,
            String perum,
            String kota,
            String kabupaten,
            String kodePos,
            String negara
    ) {
        Alamat address = new Alamat();
        address.setNomorJalan(nomorJalan);
        address.setNamaJalan(namaJalan);
        address.setPerum(perum);
        address.setKota(kota);
        address.setKabupaten(kabupaten);
        address.setKodePos(kodePos);
        address.setNegara(negara);
        return address;
    }

    private JamOperasi createStandardOperatingHours(
            String weekdayOpen,
            String weekdayClose,
            String weekendOpen,
            String weekendClose
    ) {
        RentangWaktu weekday = new RentangWaktu();
        weekday.setWaktuBuka(weekdayOpen);
        weekday.setWaktuTutup(weekdayClose);

        RentangWaktu weekend = new RentangWaktu();
        weekend.setWaktuBuka(weekendOpen);
        weekend.setWaktuTutup(weekendClose);

        JamOperasi hours = new JamOperasi();
        hours.setSenin(weekday);
        hours.setSelasa(weekday);
        hours.setRabu(weekday);
        hours.setKamis(weekday);
        hours.setJumat(weekend);
        hours.setSabtu(weekend);
        hours.setMinggu(weekend);

        return hours;
    }
}
