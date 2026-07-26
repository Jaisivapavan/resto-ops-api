package com.sivapavan.restoops.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {

	
	@NotBlank(message = "Full Name is Required")
	private String fullName;
	
	@NotBlank(message = "Email is Required")
	@Email(message = "Email must be Valid")
	private String email;
	
	@NotBlank(message = "Password is Required")
	@Size(min = 8, max = 12, message = "Password must be min 8 letters, and max 12 letters")
	private String password;
	
	@NotBlank(message = "Restaurant Name is Required")
	private String restaurantName;

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	
	
	
}
