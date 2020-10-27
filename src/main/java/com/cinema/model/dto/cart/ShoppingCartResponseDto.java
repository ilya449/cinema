package com.cinema.model.dto.cart;

import java.util.List;
import lombok.Data;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private List<Long> ticketsIds;
}
