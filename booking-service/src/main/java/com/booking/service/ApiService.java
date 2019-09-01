package com.booking.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.pojo.BookingEntity;
import com.booking.repository.ApiRepository;

@Service
public class ApiService {

	private ApiRepository repository;

	@Autowired
	public ApiService(ApiRepository repository) {
		this.repository = repository;
	}

	public BookingEntity getBookingDetailsByBookingId(String bookingId) {

		Optional<BookingEntity> entity = this.repository.findById(bookingId);
		if (entity.isPresent()) {
			return entity.get();
		} else {
			return null;
		}
	}

	public List<BookingEntity> getBookingDetailsByUser(String user) {

		return repository.findByGuestDetailsUsername(user);
	}

	public BookingEntity saveBookingEntity(BookingEntity entity) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String invoiceId = entity.getEntityType().substring(0, 4).toUpperCase() + format.format(entity.getFromDate()) + format.format(entity.getToDate());
		entity.setInvoiceId(invoiceId);
		
		return repository.save(entity);
	}

}
