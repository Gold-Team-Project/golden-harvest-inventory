package com.teamgold.goldenharvest.domain.inventory.command.application.event.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PurchaseOrderCreatedEvent(
        @NotBlank(message = "주문 id는 필수입니다.")
        String purchaseOrderId,

        @NotNull(message = "주문 생성일은 필수입니다.")
        LocalDate createdAt,

        @NotBlank(message = "SKU 번호는 필수입니다.")
        String skuNo,

        @Min(value = 1, message = "주문 수량은 1 이상이여야 합니다.")
        int quantity
) { }
