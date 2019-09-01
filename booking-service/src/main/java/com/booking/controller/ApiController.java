package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.pojo.BookingEntity;
import com.booking.service.ApiService;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/booking-service")
public class ApiController {

	private ApiService service;
	
	@Autowired
	public ApiController(ApiService service) {
		this.service = service;
	}
	
	@GetMapping("/booking/{bookingId}")
	public ResponseEntity<BookingEntity> getBookingDetailsById(@PathVariable(value = "bookingId") String bookingId) {
		return new ResponseEntity<BookingEntity>(service.getBookingDetailsByBookingId(bookingId), HttpStatus.OK);
	}
	
	@GetMapping("/booking")
	public ResponseEntity<List<BookingEntity>> getBookingDetailsByUser(@RequestHeader("USER") String user) {
		return new ResponseEntity<List<BookingEntity>>(service.getBookingDetailsByUser(user), HttpStatus.OK);
	}
	
	@PostMapping("/booking")
	public ResponseEntity<BookingEntity> bookEntity(@RequestBody BookingEntity entity){
		return new ResponseEntity<BookingEntity>(service.saveBookingEntity(entity), HttpStatus.OK);
	}
}
