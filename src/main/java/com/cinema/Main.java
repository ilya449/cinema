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
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    private static final Logger logger = Logger.getLogger(Main.class);

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
        logger.info("Created movies:");
        movieService.getAll().forEach(logger::info);

        CinemaHall redHall = new CinemaHall(50, "red hall");
        CinemaHall vipHall = new CinemaHall(10, "vip hall");
        CinemaHallService cinemaHallService = context.getBean(CinemaHallService.class);

        cinemaHallService.add(redHall);
        cinemaHallService.add(vipHall);
        logger.info("Created cinema halls:");
        cinemaHallService.getAll().forEach(logger::info);

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

        logger.info("Today movie sessions for movie: " + firstMovie);
        logger.info(movieSessionService
                .findAvailableSessions(firstMovie.getId(), LocalDate.now()));

        logger.info("Today movie sessions for movie: " + secondMovie);
        logger.info(movieSessionService
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

        logger.info("User by email: " + userBob.getEmail());
        logger.info(userService.findByEmail("bob@gmail.com"));

        AuthenticationService authenticationService
                = context.getBean(AuthenticationService.class);

        User registeredUser = authenticationService
                .register("newUserEmail@mail.com", "!pass_*_pass!");

        logger.info("Logging in user:");
        logger.info(authenticationService
                .login(registeredUser.getEmail(), "!pass_*_pass!"));

        ShoppingCartService shoppingCartService = context.getBean(ShoppingCartService.class);
        User executedRegisteredUser = userService.findByEmail("newUserEmail@mail.com").get();
        shoppingCartService.addSession(todayMorningSession, executedRegisteredUser);
        shoppingCartService.addSession(todayEveningSession, executedRegisteredUser);
        logger.info("Get new user's shopping cart: ");
        logger.info(shoppingCartService.getByUser(executedRegisteredUser));
        shoppingCartService.clear(shoppingCartService.getByUser(executedRegisteredUser));
        logger.info("New user's shopping cart after clearing: ");
        logger.info(shoppingCartService.getByUser(executedRegisteredUser));

        shoppingCartService.addSession(todayMorningSession, executedRegisteredUser);
        shoppingCartService.addSession(todayEveningSession, executedRegisteredUser);
        ShoppingCart cart = shoppingCartService.getByUser(executedRegisteredUser);
        OrderService orderService = context.getBean(OrderService.class);
        logger.info("Completed order:");
        logger.info(orderService.completeOrder(cart.getTickets(), cart.getUser()));
        logger.info("Order history for user: " + cart.getUser());
        logger.info(orderService.getOrderHistory(cart.getUser()));
    }
}
