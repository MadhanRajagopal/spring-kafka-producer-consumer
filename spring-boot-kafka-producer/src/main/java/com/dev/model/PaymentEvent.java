package com.dev.model;


public record PaymentEvent(String paymentId,
                           String paymentMode,
                           Double amount,
                           PaymentStatus status,
                           long eventTimestamp
                            ) {
}
