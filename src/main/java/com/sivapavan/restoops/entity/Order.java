package com.sivapavan.restoops.entity;


import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "restaurant_id", nullable = false)
	private Restaurant restaurant;

	@Column(nullable = false, unique = true)
	private String orderNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private OrderStatus status = OrderStatus.PLACED;

	@Column(nullable = false)
	private BigDecimal totalAmount = BigDecimal.ZERO;

	private String customerName;

	private String tableNumber;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

	@Version
	private Long version;

	public void addItem(OrderItem item) {
		items.add(item);
		item.setOrder(this);
	}
}
