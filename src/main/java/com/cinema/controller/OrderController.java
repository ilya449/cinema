package com.cinema.controller;

import com.cinema.model.User;
import com.cinema.model.dto.order.OrderResponseDto;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.service.mapper.OrderDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final OrderDtoMapper mapper;

    public OrderController(OrderService orderService, ShoppingCartService shoppingCartService,
                           UserService userService, OrderDtoMapper mapper) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping
    public void completeOrder(@RequestBody Long userId) {
        User user = userService.get(userId);
        orderService.completeOrder(shoppingCartService.getByUser(user).getTickets(), user);
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders(@RequestParam Long userId) {
        return orderService.getOrderHistory(userService.get(userId)).stream()
                .map(mapper::getOrderResponseDto)
                .collect(Collectors.toList());
    }
}
