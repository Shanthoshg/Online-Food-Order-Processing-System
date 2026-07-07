package com.example.orderservice.service;

import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
// It is used to send messages to ActiveMQ.
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
// It is used to send messages to ActiveMQ.
// Either all operations success or everything is rolled back

import java.util.List;

@Service("orderService")
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    public Order createOrder(Order order) {
        order.setStatus("PLACED");
        Order savedOrder = orderRepository.save(order);
        System.out.println("[OrderService] Order #" + savedOrder.getId() + " - Status: PLACED");
        
        // Publish to ActiveMQ
        jmsTemplate.convertAndSend("order.created", savedOrder.getId());
        System.out.println("[OrderService] Order #" + savedOrder.getId() + " - Published to order.created queue");
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
            
            // Console logging matching requirement
            if ("PAYMENT_PROCESSING".equals(status)) {
                System.out.println("[OrderService] Order #" + orderId + " - Status: PAYMENT_PROCESSING, Workflow started");
            } else if ("DELIVERED".equals(status)) {
                System.out.println("[OrderService] Order #" + orderId + " - Workflow COMPLETE");
            } else if ("CANCELLED".equals(status)) {
                System.out.println("[OrderService] Order #" + orderId + " - CANCELLED");
            } else {
                System.out.println("[OrderService] Order #" + orderId + " - Status updated to " + status);
            }
        }
    }
}
