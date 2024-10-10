package br.com.innoacct.payment_service.services.payment;

import br.com.innoacct.payment_service.enums.ValidPaymentTypeEnum;
import br.com.innoacct.payment_service.services.sns.AwsSnsService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PaymentService {

    private final AwsSnsService awsSnsService;

    public PaymentService(AwsSnsService awsSnsService) {
        this.awsSnsService = awsSnsService;
    }

    public void validateOrderPayment(String message) throws Exception{
        System.out.println("Processando tipo de pagamento do pedido...");

        JSONObject queueMessage = new JSONObject(message);
        String messageJsonString = queueMessage.getString("Message");

        JSONObject messageObject = null;
        try {
            messageObject = new JSONObject(messageJsonString);
        }catch (Exception e){
            log.error("Error: ", e);
            return;
        }
        String paymentType = messageObject.getString("paymentType");

        PaymentHistoricDTO paymentHistoricLog = PaymentHistoricDTO.builder()
                .orderId(UUID.fromString(messageObject.getString("id")))
                .paymentStatus(isValidPaymentType(paymentType) ? PaymentStatusEnum.APPROVED.name() : PaymentStatusEnum.RECUSED.name())
                .build();

        awsSnsService.publish(paymentHistoricLog.toString());
    }

    private boolean isValidPaymentType(String paymentType){
        List<String> validPaymentTypes = Arrays.stream(ValidPaymentTypeEnum.values())
                .map(ValidPaymentTypeEnum::name)
                .map(String::toUpperCase)
                .toList();

        return validPaymentTypes.contains(paymentType.toUpperCase());
    }
}
