package com.example.kitchenservice.repository;

import com.example.kitchenservice.model.KitchenTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KitchenRepository extends JpaRepository<KitchenTicket, Long> {
}
