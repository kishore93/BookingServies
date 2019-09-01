package com.banquet.server.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquet.server.pojo.BanquetHallEntity;

@Repository
public interface ApiRepository extends MongoRepository<BanquetHallEntity, String>{
	
}
