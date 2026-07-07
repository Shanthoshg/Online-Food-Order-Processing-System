package com.example.paymentservice.worker;

import com.example.paymentservice.model.Payment;
import com.example.paymentservice.repository.PaymentRepository;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@ExternalTaskSubscription("payment-processing")
public class PaymentWorker implements ExternalTaskHandler {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Long orderId = externalTask.getVariable("orderId");

        System.out.println("[PaymentService] Order #" + orderId + " - Payment processing started");

        // Read data from Camunda process variables (set by OrderCreatedConsumer at process start)
        String customerName = externalTask.getVariable("customerName");
        Object amountVar = externalTask.getVariable("amount");
        Double amount = amountVar != null ? ((Number) amountVar).doubleValue() : 0.0;

        if (customerName == null) {
            customerName = "";
        }

        boolean success = true;
        if (customerName.toLowerCase().contains("fail") || customerName.toLowerCase().contains("cancel")) {
            success = false;
        }

        String status = success ? "SUCCESS" : "FAILED";
        Payment payment = new Payment(orderId, amount, status);
        paymentRepository.save(payment);

        System.out.println("[PaymentService] Order #" + orderId + " - Payment processing... " + status);

        Map<String, Object> variables = new HashMap<>();
        variables.put("paymentSuccess", success);

        externalTaskService.complete(externalTask, variables);
    }
}
