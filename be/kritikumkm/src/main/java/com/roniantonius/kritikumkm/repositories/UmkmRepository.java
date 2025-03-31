package com.roniantonius.kritikumkm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.roniantonius.kritikumkm.domain.entities.Umkm;

@Repository
public interface UmkmRepository extends ElasticsearchRepository<Umkm, String>{
	// TODO: several custom query to implement business logic
	// sort umkm berdasarkan bintang (lebih dari 4), etc
	
	// terapin pagination
	Page<Umkm> findByRataRatingGreaterThanEqual(Float minRating, Pageable pageable);
	
	// berdasarkan nama dan tipe konsumse dan sekaligus rataRating
	@Query("{" +
            "  \"bool\": {" +
            "    \"must\": [" + // kueri pertama bersifat 'must' yang mengambil nilai dari parameter construct indeks ke-1, 
            "      {\"range\": {\"rataRating\": {\"gte\": ?1}}}" + // 'gte' = greater than equal
            "    ]," +
            "    \"should\": [" + // kueri kedua berisi dua fuzzy fungtional yang opsional
            "      {\"fuzzy\": {\"nama\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}," + // mencari berdsaarkan nama dari parameter indeks-0
            "      {\"fuzzy\": {\"tipeKonsumsi\": {\"value\": \"?0\", \"fuzziness\": \"AUTO\"}}}" + // sama ya,
            "    ]," +
            "    \"minimum_should_match\": 1" + // artinya dari kedua kuery fuzzy harus ada satu yang match
            "  }" +
            "}" +
            "}")
	Page<Umkm> findByQueryAndMinRating(String query, Float minRating, Pageable pageable);
	
	// kueri berdarkan geolokasi
	@Query("{" +
            "  \"bool\": {" +
            "    \"must\": [" +
            "      {\"geo_distance\": {" + // return all record within certain radius
            "        \"distance\": \"?2km\"," + // dua variabel geo_distance dan distance itu bawaan dari spring geo location
            "        \"geoLocation\": {" +
            "          \"lat\": ?0," +
            "          \"lon\": ?1" +
            "        }" +
            "      }}" +
            "    ]" +
            "  }" +
            "}")
	Page<Umkm> findByLocationNear(Float latitude, Float longitude, Float radiusKm, Pageable pageable);
}