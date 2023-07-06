package com.programmintechie.service;

import com.programmintechie.dto.InventoryResponse;
import com.programmintechie.dto.OrderDTO;
import com.programmintechie.entity.Order;
import com.programmintechie.entity.OrderLineItem;
import com.programmintechie.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems = orderDTO.getOrderLineItems().stream().map(item -> {
            OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setSkuCode(item.getSkuCode());
            orderLineItem.setPrice(item.getPrice());
            orderLineItem.setQuantity(item.getQuantity());
            orderLineItem.setOrder(order);
            return orderLineItem;
        }).toList();

        order.setOrderLineItems(orderLineItems);

        List<String> skuCodes = order.getOrderLineItems().stream().map(OrderLineItem::getSkuCode).toList();

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:9092/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve().bodyToMono(InventoryResponse[].class).block();

        boolean result = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isInStock);

        if (result) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }

    }

}
