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
@Table(name = "role")
public class Role extends BaseEntity {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, unique = true)
	private RoleName name;


	public void setName(RoleName name) {
		this.name = name;
	}
	public RoleName getName() {
		return name;
	}
	public Role(RoleName name) {
		super();
		this.name = name;
	}
	
	public Role() {}
	
}
