package com.structural_patterns.composite_pattern.controller;

import com.structural_patterns.composite_pattern.domain.Box;
import com.structural_patterns.composite_pattern.domain.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private Map<String, Box> orders = new HashMap<>();

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, String> request) {
        String orderName = request.get("name");
        Box newOrder = new Box(orderName);
        orders.put(orderName, newOrder);
        return ResponseEntity.ok("Order " + orderName + " created!");
    }

    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestParam String orderName, @RequestBody Map<String, Object> request) {
        Box order = orders.get(orderName);
        if (order != null) {
            String productName = (String) request.get("name");
            double productPrice = ((Number) request.get("price")).doubleValue();
            Product product = new Product(productName, productPrice);
            order.addItem(product);
            return ResponseEntity.ok("Product " + productName + " added to order " + orderName);
        }
        return ResponseEntity.badRequest().body("Order not found!");
    }

    @PostMapping("/addBox")
    public ResponseEntity<String> addBox(@RequestParam String orderName, @RequestBody Map<String, String> request) {
        Box parentBox = orders.get(orderName);
        if (parentBox != null) {
            String boxName = request.get("name");
            Box newBox = new Box(boxName);
            parentBox.addItem(newBox);
            orders.put(boxName, newBox); // Guardamos la caja en el mapa `orders` para futuras referencias
            return ResponseEntity.ok("Box " + boxName + " added to order " + orderName);
        }
        return ResponseEntity.badRequest().body("Order not found!");
    }

    @GetMapping("/calculatePrice")
    public ResponseEntity<Double> calculatePrice(@RequestParam String orderName) {
        Box order = orders.get(orderName);
        if (order != null) {
            return ResponseEntity.ok(order.getPrice());
        }
        return ResponseEntity.badRequest().body(0.0);
    }
}