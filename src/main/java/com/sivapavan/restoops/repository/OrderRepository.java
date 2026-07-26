package com.sivapavan.restoops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivapavan.restoops.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	 List<Order> findByRestaurantId(Long restaurantId);
}
