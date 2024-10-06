package br.com.innoacct.order_service.dtos;

import br.com.innoacct.order_service.enums.PaymentTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {
    @NotNull
    private UUID productId;
    @NotNull
    private Integer quantity;
    @NotNull
    private PaymentTypeEnum paymentType;
}
