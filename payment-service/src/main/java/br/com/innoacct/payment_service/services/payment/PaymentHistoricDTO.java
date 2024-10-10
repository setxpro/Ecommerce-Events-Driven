package br.com.innoacct.payment_service.services.payment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentHistoricDTO {

    private UUID orderId;
    private String paymentStatus;

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("orderId", orderId);
        json.put("paymentStatus", paymentStatus);

        return json.toString();
    }

}
