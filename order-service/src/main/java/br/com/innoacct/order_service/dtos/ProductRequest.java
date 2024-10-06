package br.com.innoacct.order_service.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {
    @NotNull
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer quantity;
}
