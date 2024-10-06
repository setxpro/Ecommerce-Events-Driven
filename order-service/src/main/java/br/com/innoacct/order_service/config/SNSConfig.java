package br.com.innoacct.order_service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.Topic;

@Configuration
public class SNSConfig {
    @Value("${aws.region}")
    private String region;
    @Value("${aws.sns.orders-topic}")
    private String ordersTopicArn;

    @Bean
    public AmazonSNS amazonSNS() {
        return AmazonSNSClientBuilder.standard()
        .withCredentials(new DefaultAWSCredentialsProviderChain())
        .withRegion(region)
        .build();
    }

    @Bean(name = "ordersTopic")
    public Topic snsOrdersTopic() {
        return new Topic().withTopicArn(ordersTopicArn);
    }
}
