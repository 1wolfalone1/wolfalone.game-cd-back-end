package com.wolfalone.gamecdbackend.controller;

import com.wolfalone.gamecdbackend.service.OrderDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping("order-details/{orderId}")
    public ResponseEntity<?> getOrderDetails(@PathVariable int orderId) {
        return orderDetailsService.getOrderDetailsByOrderId(orderId);
    }
}
