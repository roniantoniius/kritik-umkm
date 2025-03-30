package com.roniantonius.kritikumkm.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.roniantonius.kritikumkm.domain.entities.Umkm;

@Repository
public interface UmkmRepository extends ElasticsearchRepository<Umkm, String>{
	// TODO: several custom query to implement business logic
}
