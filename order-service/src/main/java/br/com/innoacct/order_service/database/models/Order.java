package br.com.innoacct.order_service.database.models;

import br.com.innoacct.order_service.dtos.OrderRequest;
import br.com.innoacct.order_service.enums.PaymentStatusEnum;
import org.json.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "orders")
public class Order {

    @Id
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal amount;
    private LocalDateTime orderTime;
    private String paymentStatus;
    private String paymentType;

    public Order(OrderRequest request, Product product){
        this.id = UUID.randomUUID();
        this.productId = request.getProductId();
        this.quantity = request.getQuantity();
        this.amount = product.getPrice().multiply(new BigDecimal(request.getQuantity()));
        this.orderTime = LocalDateTime.now();
        this.paymentStatus = PaymentStatusEnum.PENDING.name().toUpperCase();
        this.paymentType = request.getPaymentType().name().toUpperCase();
    }

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("productId", productId);
        json.put("quantity", quantity);
        json.put("amount", amount);
        json.put("paymentType", paymentType);
        json.put("paymentStatus", paymentStatus);
        json.put("orderTime", orderTime);

        return json.toString();
    }
}
