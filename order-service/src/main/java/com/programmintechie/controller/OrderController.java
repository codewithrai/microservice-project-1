package com.programmintechie.controller;

import com.programmintechie.dto.OrderDTO;
import com.programmintechie.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {
        orderService.placeOrder(orderDTO);
        return new ResponseEntity<>("Order created successfully", HttpStatus.OK);
    }

}
