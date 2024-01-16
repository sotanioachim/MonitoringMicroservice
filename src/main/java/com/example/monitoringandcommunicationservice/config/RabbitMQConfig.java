package com.example.monitoringandcommunicationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.sensor}")
    private String queueSensor;
    @Value("${rabbitmq.queue.device}")
    private String queueDevice;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.sensor}")
    private String routingKeySensor;
    @Value("${rabbitmq.routing.device}")
    private String routingKeyDevice;

    @Bean
    public Queue queueSensor(){
        return new Queue(queueSensor);
    }

    @Bean
    public Queue queueDevice(){ return new Queue(queueDevice);}

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingSensor(){
        return BindingBuilder
                .bind(queueSensor())
                .to(exchange())
                .with(routingKeySensor);
    }

    @Bean
    public Binding bindingDevice(){
        return BindingBuilder
                .bind(queueDevice())
                .to(exchange())
                .with(routingKeyDevice);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
