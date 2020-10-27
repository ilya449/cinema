package com.cinema.service.mapper;

import com.cinema.model.ShoppingCart;
import com.cinema.model.Ticket;
import com.cinema.model.dto.cart.ShoppingCartResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartDtoMapper {
    public ShoppingCartResponseDto getShoppingCartResponseDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto dto = new ShoppingCartResponseDto();
        List<Long> ticketIds = shoppingCart.getTickets().stream()
                .map(Ticket::getId)
                .collect(Collectors.toList());
        dto.setId(shoppingCart.getId());
        dto.setUserId(shoppingCart.getUser().getId());
        dto.setTicketsIds(ticketIds);
        return dto;
    }
}
