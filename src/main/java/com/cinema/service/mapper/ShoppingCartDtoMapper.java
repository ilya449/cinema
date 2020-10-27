package com.cinema.service.mapper;

import com.cinema.model.ShoppingCart;
import com.cinema.model.Ticket;
import com.cinema.model.dto.cart.ShoppingCartResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartDtoMapper {
    public ShoppingCartResponseDto mapToShoppingCartResponseDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto responseDto = new ShoppingCartResponseDto();
        List<Long> ticketIds = shoppingCart.getTickets().stream()
                .map(Ticket::getId)
                .collect(Collectors.toList());
        responseDto.setId(shoppingCart.getId());
        responseDto.setTicketsIds(ticketIds);
        return responseDto;
    }
}
