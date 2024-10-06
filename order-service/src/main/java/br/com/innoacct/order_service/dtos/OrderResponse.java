package br.com.innoacct.order_service.dtos;

import br.com.innoacct.order_service.database.models.Order;
import br.com.innoacct.order_service.database.models.Product;
import br.com.innoacct.order_service.database.repositories.IProductRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {
    private UUID id;
    private Integer quantity;
    private BigDecimal amount;
    private LocalDateTime orderTime;
    private String paymentStatus;
    private String paymentType;
    private Product product;

    public OrderResponse(Order order, IProductRepository iProductRepository){
        this.id = order.getId();
        this.quantity = order.getQuantity();
        this.amount = order.getAmount();
        this.orderTime = order.getOrderTime();
        this.paymentStatus = order.getPaymentStatus();
        this.paymentType = order.getPaymentType();
        this.product = iProductRepository.findById(order.getProductId())
                .orElse(null);
    }

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("quantity", quantity);
        json.put("amount", amount);
        json.put("orderTime", orderTime);
        json.put("paymentStatus", paymentStatus);
        json.put("paymentType", paymentType);
        json.put("product", product.toString());

        return json.toString();
    }
}
