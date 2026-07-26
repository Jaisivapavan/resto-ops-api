package com.sivapavan.restoops.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{

	
	 @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "order_id", nullable = false)
	    private Order order;

	    @Column(nullable = false)
	    private String itemName;

	    @Column(nullable = false)
	    private Integer quantity;

	    @Column(nullable = false)
	    private BigDecimal unitPrice;

	    public BigDecimal getSubtotal() {
	        return unitPrice.multiply(BigDecimal.valueOf(quantity));
	    }
	    public void setOrder(Order order) {
	        this.order = order;
	    }

}
