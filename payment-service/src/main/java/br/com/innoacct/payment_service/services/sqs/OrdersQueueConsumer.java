package br.com.innoacct.payment_service.services.sqs;

import br.com.innoacct.payment_service.services.payment.PaymentService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class OrdersQueueConsumer {

    private final PaymentService paymentService;

    public OrdersQueueConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @SqsListener("orders-queue")
    public void listen(String message) throws Exception{
        paymentService.validateOrderPayment(message);
    }
}
