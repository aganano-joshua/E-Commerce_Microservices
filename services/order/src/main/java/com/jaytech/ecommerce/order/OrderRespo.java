package com.jaytech.ecommerce.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRespo extends JpaRepository<Order, Integer> {
}
