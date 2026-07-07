package com.example.orderservice.messaging;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderCreatedConsumer {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private OrderRepository orderRepository;

    @JmsListener(destination = "order.created")
    public void receiveOrder(Long orderId) {
        System.out.println("[OrderService] ActiveMQ Consumer - Received order.created event for Order #" + orderId);

        // Fetch order details to pass as process variables — avoids cross-service DB queries
        Order order = orderRepository.findById(orderId).orElse(null);

        Map<String, Object> variables = new HashMap<>();
        variables.put("orderId", orderId);
        if (order != null) {
            variables.put("customerName", order.getCustomerName());
            variables.put("item", order.getItem());
            variables.put("amount", order.getAmount());
        }

        // Start Camunda process instance
        runtimeService.startProcessInstanceByKey("order-processing", variables);
    }
}
