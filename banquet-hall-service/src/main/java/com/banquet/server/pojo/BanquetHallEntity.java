package com.banquet.server.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "banquet_halls")
public class BanquetHallEntity {

	@Id
	private String id;

	private String name;
	private String address;
	// private String imageData;
	private List<String> amenities;
	private CoOrdinates coOrdinates;
	private Double bookingPrice;

	public BanquetHallEntity() {

	}

	public BanquetHallEntity(String id, String name, String address, List<String> amenities, CoOrdinates coOrdinates,
			Double bookingPrice) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.amenities = amenities;
		this.coOrdinates = coOrdinates;
		this.bookingPrice = bookingPrice;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<String> amenities) {
		this.amenities = amenities;
	}

	public CoOrdinates getCoOrdinates() {
		return coOrdinates;
	}

	public void setCoOrdinates(CoOrdinates coOrdinates) {
		this.coOrdinates = coOrdinates;
	}

	public Double getBookingPrice() {
		return bookingPrice;
	}

	public void setBookingPrice(Double bookingPrice) {
		this.bookingPrice = bookingPrice;
	}

}

class CoOrdinates {

	private Double latitiude;
	private Double longitude;

	public CoOrdinates() {

	}

	public CoOrdinates(Double latitiude, Double longitude) {
		this.latitiude = latitiude;
		this.longitude = longitude;
	}

	public Double getLatitiude() {
		return latitiude;
	}

	public void setLatitiude(Double latitiude) {
		this.latitiude = latitiude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "CoOrdinates [latitiude=" + latitiude + ", longitude=" + longitude + "]";
	}

}