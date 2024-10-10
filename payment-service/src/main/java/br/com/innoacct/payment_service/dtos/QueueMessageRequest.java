package br.com.innoacct.payment_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueueMessageRequest {

    private String Type;
    private String MessageId;
    private String TopicArn;
    private OrderResponse Message;

}
