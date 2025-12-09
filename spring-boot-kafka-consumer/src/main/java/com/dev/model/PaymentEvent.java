package com.dev.model;


public record PaymentEvent( String paymentId,
                            String paymentMode,
                            Double amount,
                            String status,
                            long eventTimestamp
                            ) {
}
