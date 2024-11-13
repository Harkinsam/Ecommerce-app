package com.victoruk.Ecommerce.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.victoruk.Ecommerce.entity.Order;
import com.victoruk.Ecommerce.entity.PaymentStatus;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentDTO {

    private Long id;
    private BigDecimal amount;
    private String method;
    private PaymentStatus paymentStatus;
    private Order order;
}
