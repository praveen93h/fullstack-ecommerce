package com.example.ecom.controller;

import com.example.ecom.model.*;
import com.example.ecom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private UserRepository userRepo;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody Map<String,Object> body, Principal principal) {
        List<Map<String,Object>> items = (List<Map<String,Object>>) body.get("items");
        if (principal == null) return ResponseEntity.status(401).body(Map.of("error","unauthorized"));
        String username = principal.getName();
        User u = userRepo.findByUsername(username).orElseThrow();
        double total = 0;
        Order order = new Order();
        order.setUser(u);
        for (Map<String,Object> it : items) {
            Number pidN = (Number) it.get("productId");
            int qty = ((Number) it.get("quantity")).intValue();
            long pid = pidN.longValue();
            Product p = productRepo.findById(pid).orElseThrow();
            if (p.getStock() < qty) return ResponseEntity.badRequest().body(Map.of("error","not enough stock"));
            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductName(p.getName());
            oi.setPrice(p.getPrice().doubleValue());
            oi.setQuantity(qty);
            oi.setOrder(order);
            order.getItems().add(oi);
            total += p.getPrice().doubleValue() * qty;
            p.setStock(p.getStock() - qty);
            productRepo.save(p);
        }
        order.setTotalAmount(total);
        order.setStatus("PAID");
        order.setTransactionId(UUID.randomUUID().toString());
        orderRepo.save(order);
        return ResponseEntity.ok(Map.of("status","success","transactionId", order.getTransactionId()));
    }
}
