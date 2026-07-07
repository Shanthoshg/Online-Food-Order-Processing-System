package com.example.deliveryservice.worker;

import com.example.deliveryservice.model.Delivery;
import com.example.deliveryservice.repository.DeliveryRepository;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription("delivery-service")
public class DeliveryWorker implements ExternalTaskHandler {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Long orderId = externalTask.getVariable("orderId");

        System.out.println("[DeliveryService] Order #" + orderId + " - Driver assignment started");

        // Create and save delivery as ASSIGNED
        Delivery delivery = new Delivery(orderId, "John Doe", "ASSIGNED");
        delivery = deliveryRepository.save(delivery);

        // Simulate delivery transit time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Update status to DELIVERED
        delivery.setStatus("DELIVERED");
        deliveryRepository.save(delivery);

        System.out.println("[DeliveryService] Order #" + orderId + " - Driver assigned, delivering... DELIVERED");

        // Complete the task in Camunda
        externalTaskService.complete(externalTask);
    }
}
