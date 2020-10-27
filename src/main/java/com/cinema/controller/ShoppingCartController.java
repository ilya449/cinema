package com.cinema.controller;

import com.cinema.model.dto.cart.ShoppingCartResponseDto;
import com.cinema.service.MovieSessionService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import com.cinema.service.mapper.ShoppingCartDtoMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping-carts")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;
    private final MovieSessionService movieSessionService;
    private final UserService userService;
    private final ShoppingCartDtoMapper mapper;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  MovieSessionService movieSessionService,
                                  UserService userService,
                                  ShoppingCartDtoMapper mapper) {
        this.shoppingCartService = shoppingCartService;
        this.movieSessionService = movieSessionService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/movie-sessions")
    public void addMovieSession(@RequestParam Long movieSessionId, @RequestParam Long userId) {
        shoppingCartService.addSession(
                movieSessionService.get(movieSessionId), userService.get(userId));
    }

    @GetMapping("/by-user")
    public ShoppingCartResponseDto getByUser(@RequestParam Long userId) {
        return mapper.mapToShoppingCartResponseDto(
                shoppingCartService.getByUser(userService.get(userId)));
    }
}
