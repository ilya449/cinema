package com.cinema.service.mapper;

import com.cinema.model.Order;
import com.cinema.model.dto.order.OrderResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper {
    public OrderResponseDto getOrderResponseDto(Order order) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTickets(order.getTickets());
        return dto;
    }
}
