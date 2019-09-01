package com.booking.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.booking.pojo.BookingEntity;

@Repository
public interface ApiRepository extends MongoRepository<BookingEntity, String>{

	public List<BookingEntity> findByGuestDetailsUsername(String username);
	
}
