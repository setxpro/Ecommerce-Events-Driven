package br.com.innoacct.payment_service.dtos;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private BigDecimal price;
    private Integer quantity;

    @Override
    public String toString(){
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("name", name);
        json.put("quantity", quantity);
        json.put("price", price);

        return json.toString();
    }
}
