package com.jaytech.payment.payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRespository extends JpaRepository<Payment, Integer> {
}
