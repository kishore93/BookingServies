package com.booking.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "bookings")
public class BookingEntity {

	@Id
	private String invoiceId;

	private String entityType;
	private String entityId;
	private Date fromDate;
	private Date toDate;
	private Guest guestDetails;
	private Invoice invoiceDetails;

	public BookingEntity() {

	}

	public BookingEntity(String invoiceId, String entityType, String entityId, Date fromDate, Date toDate,
			Guest guestDetails, Invoice invoiceDetails) {
		this.invoiceId = invoiceId;
		this.entityType = entityType;
		this.entityId = entityId;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.guestDetails = guestDetails;
		this.invoiceDetails = invoiceDetails;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
		this.getInvoiceDetails().setInvoiceId(invoiceId);
		this.getInvoiceDetails().calculate();
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Guest getGuestDetails() {
		return guestDetails;
	}

	public void setGuestDetails(Guest guestDetails) {
		this.guestDetails = guestDetails;
	}

	public Invoice getInvoiceDetails() {
		return invoiceDetails;
	}

	public void setInvoiceDetails(Invoice invoiceDetails) {
		this.invoiceDetails = invoiceDetails;
	}

	@Override
	public String toString() {
		return "BookingEntity [invoiceId=" + invoiceId + ", entityType=" + entityType + ", entityId=" + entityId
				+ ", fromDate=" + fromDate + ", toDate=" + toDate + ", guestDetails=" + guestDetails
				+ ", invoiceDetails=" + invoiceDetails + "]";
	}

}

class Guest {

	private String username;
	private String guestName;
	private String email;
	private Long phone;
	private String address;
	private String idType;
	private String idNumber;

	public Guest() {

	}

	public Guest(String username, String guestName, String email, Long phone, String address, String idType,
			String idNumber) {
		this.username = username;
		this.guestName = guestName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.idType = idType;
		this.idNumber = idNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public String toString() {
		return "Guest [username=" + username + ", guestName=" + guestName + ", email=" + email + ", phone=" + phone
				+ ", address=" + address + ", idType=" + idType + ", idNumber=" + idNumber + "]";
	}

}

class Invoice {

	private String invoiceId;
	private double pricePerDay;
	private int numberOfDays;
	private double totalAmount;
	private double SGST;
	private double CGST;
	private double totalTax;

	public Invoice() {

	}

	public Invoice(String invoiceId, double pricePerDay, int numberOfDays) {
		this.invoiceId = invoiceId;
		this.pricePerDay = pricePerDay;
		this.numberOfDays = numberOfDays;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public double getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public int getNumberOfDays() {
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getSGST() {
		return SGST;
	}

	public void setSGST(double sGST) {
		SGST = sGST;
	}

	public double getCGST() {
		return CGST;
	}

	public void setCGST(double cGST) {
		CGST = cGST;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public void calculate() {
		this.setTotalAmount(this.getPricePerDay() * this.getNumberOfDays());
		this.setSGST(this.getTotalAmount() * 0.05);
		this.setCGST(this.getTotalAmount() * 0.05);
		this.setTotalTax(this.getSGST() + this.getCGST());
	}

	@Override
	public String toString() {
		return "Invoice [invoiceId=" + invoiceId + ", pricePerDay=" + pricePerDay + ", numberOfDays=" + numberOfDays
				+ ", totalAmount=" + totalAmount + ", SGST=" + SGST + ", CGST=" + CGST + ", totalTax=" + totalTax + "]";
	}

}
