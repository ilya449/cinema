package com.cinema;

import com.cinema.config.AppConfig;
import com.cinema.exception.AuthenticationException;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.model.ShoppingCart;
import com.cinema.model.User;
import com.cinema.service.AuthenticationService;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import com.cinema.service.OrderService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Log4j
public class Main {
    public static void main(String[] args) throws AuthenticationException {
        Movie firstMovie = new Movie();
        firstMovie.setTitle("Fast and Furious");
        firstMovie.setDescription("18+");

        Movie secondMovie = new Movie();
        secondMovie.setTitle("How to Train Your Dragon");
        secondMovie.setDescription("Cartoon");

        AnnotationConfigApplicationContext context
                = new AnnotationConfigApplicationContext(AppConfig.class);

        MovieService movieService = context.getBean(MovieService.class);

        movieService.add(firstMovie);
        movieService.add(secondMovie);
        log.info("Created movies:");
        movieService.getAll().forEach(log::info);

        CinemaHall redHall = new CinemaHall(50, "red hall");
        CinemaHall vipHall = new CinemaHall(10, "vip hall");
        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);

        cinemaHallService.add(redHall);
        cinemaHallService.add(vipHall);
        log.info("Created cinema halls:");
        cinemaHallService.getAll().forEach(log::info);

        MovieSessionService movieSessionService = context.getBean(MovieSessionService.class);

        MovieSession todayMorningSession = new MovieSession(secondMovie, redHall,
                LocalDateTime.now());
        MovieSession todayEveningSession = new MovieSession(firstMovie, vipHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 15)));
        MovieSession tomorrowSession = new MovieSession(secondMovie, redHall,
                LocalDateTime.now().plusDays(1));

        movieSessionService.add(todayMorningSession);
        movieSessionService.add(todayEveningSession);
        movieSessionService.add(tomorrowSession);

        log.info("Today movie sessions for movie: " + firstMovie);
        log.info(movieSessionService
                .findAvailableSessions(firstMovie.getId(), LocalDate.now()));

        log.info("Today movie sessions for movie: " + secondMovie);
        log.info(movieSessionService
                .findAvailableSessions(secondMovie.getId(), LocalDate.now()));

        UserService userService = context.getBean(UserService.class);

        User userBob = new User();
        userBob.setEmail("bob@gmail.com");
        userBob.setPassword("bob's_Pass");
        userService.add(userBob);

        User userAlice = new User();
        userAlice.setEmail("alice@gmail.com");
        userAlice.setPassword("alice's_pass");
        userService.add(userAlice);

        log.info("User by email: " + userBob.getEmail());
        log.info(userService.findByEmail("bob@gmail.com"));

        AuthenticationService authenticationService
                = context.getBean(AuthenticationService.class);

        User registeredUser = authenticationService
                .register("newUserEmail@mail.com", "!pass_*_pass!");

        log.info("Logging in user:");
        log.info(authenticationService
                .login(registeredUser.getEmail(), "!pass_*_pass!"));

        ShoppingCartService shoppingCartService = context.getBean(ShoppingCartService.class);
        User executedRegisteredUser = userService.findByEmail("newUserEmail@mail.com");
        shoppingCartService.addSession(todayMorningSession, executedRegisteredUser);
        shoppingCartService.addSession(todayEveningSession, executedRegisteredUser);
        log.info("Get new user's shopping cart: ");
        log.info(shoppingCartService.getByUser(executedRegisteredUser));
        shoppingCartService.clear(shoppingCartService.getByUser(executedRegisteredUser));
        log.info("New user's shopping cart after clearing: ");
        log.info(shoppingCartService.getByUser(executedRegisteredUser));

        shoppingCartService.addSession(todayMorningSession, executedRegisteredUser);
        shoppingCartService.addSession(todayEveningSession, executedRegisteredUser);
        ShoppingCart cart = shoppingCartService.getByUser(executedRegisteredUser);
        OrderService orderService = context.getBean(OrderService.class);
        log.info("Completed order:");
        log.info(orderService.completeOrder(cart.getTickets(), cart.getUser()));
        log.info("Order history for user: " + cart.getUser());
        log.info(orderService.getOrderHistory(cart.getUser()));
    }
}
