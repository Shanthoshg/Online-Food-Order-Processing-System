package com.example.kitchenservice.worker;

import com.example.kitchenservice.model.KitchenTicket;
import com.example.kitchenservice.repository.KitchenRepository;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ExternalTaskSubscription("kitchen-preparation")
public class KitchenWorker implements ExternalTaskHandler {

    @Autowired
    private KitchenRepository kitchenRepository;

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Long orderId = externalTask.getVariable("orderId");

        System.out.println("[KitchenService] Order #" + orderId + " - Kitchen preparation started");

        // Read item name from Camunda process variable (set by OrderCreatedConsumer at process start)
        String itemName = externalTask.getVariable("item");
        if (itemName == null || itemName.isEmpty()) {
            itemName = "Unknown Item";
        }

        // Create and save ticket as PREPARING
        KitchenTicket ticket = new KitchenTicket(orderId, itemName, "PREPARING");
        ticket = kitchenRepository.save(ticket);

        // Simulate preparation time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Update status to READY
        ticket.setStatus("READY");
        kitchenRepository.save(ticket);

        System.out.println("[KitchenService] Order #" + orderId + " - Kitchen ticket created, preparing food... READY");

        // Complete the task in Camunda
        externalTaskService.complete(externalTask);
    }
}
