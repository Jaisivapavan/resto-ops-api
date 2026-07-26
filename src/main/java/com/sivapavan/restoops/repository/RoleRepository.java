package com.sivapavan.restoops.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivapavan.restoops.entity.Role;
import com.sivapavan.restoops.entity.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(RoleName name);

}
