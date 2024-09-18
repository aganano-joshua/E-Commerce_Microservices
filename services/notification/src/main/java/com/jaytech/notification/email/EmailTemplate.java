package com.jaytech.notification.email;

import lombok.Getter;
import lombok.Setter;

public enum EmailTemplate {
    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payent successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order Confirmation");

    @Getter
    private final String template;
    @Getter
    private final String subject;

    EmailTemplate(String template, String subject){
        this.template = template;
        this.subject = subject;
    }
}
