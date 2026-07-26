package com.sivapavan.restoops.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name="restaurants")
public class Restaurant extends BaseEntity{

	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String slug;
	
	private String address;
	
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RestaurantStatus status = RestaurantStatus.ACTIVE;

	public Restaurant(String name, String slug, String address, String phoneNumber, RestaurantStatus status) {
		super();
		this.name = name;
		this.slug = slug;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public RestaurantStatus getStatus() {
		return status;
	}

	public void setStatus(RestaurantStatus status) {
		this.status = status;
	}
	
	public Restaurant() {}
}
