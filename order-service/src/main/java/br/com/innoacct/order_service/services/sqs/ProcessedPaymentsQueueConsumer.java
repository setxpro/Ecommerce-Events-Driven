package br.com.innoacct.order_service.services.sqs;

import br.com.innoacct.order_service.services.order.OrderService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class ProcessedPaymentsQueueConsumer {
    private final OrderService orderService;

    public ProcessedPaymentsQueueConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @SqsListener("processed-orders-queue")
    public void listen(String message) throws Exception{
        System.out.println("Payment processed");
        JSONObject queueMessage = new JSONObject(message);
        String messageJsonString = queueMessage.getString("Message");

        JSONObject messageObject = null;
        try {
            messageObject = new JSONObject(messageJsonString);
        }catch (Exception e){
            log.error("Error: ", e);
            return;
        }

        orderService.updatePaymentStatus(UUID.fromString(messageObject.getString("orderId")), messageObject.getString("paymentStatus"));
    }
}
