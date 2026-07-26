package com.sivapavan.restoops.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivapavan.restoops.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	Optional<Restaurant> findBySlug(String slug);

}
