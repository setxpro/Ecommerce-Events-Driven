package br.com.innoacct.order_service.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;


@Configuration
public class SQSConfig {

    @Value("${aws.sqs.endpoint}")
    private String amazonSqsEndpoint;

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .endpointOverride(URI.create(amazonSqsEndpoint))
                .region(Region.US_WEST_1)
                .build();
    }

}
