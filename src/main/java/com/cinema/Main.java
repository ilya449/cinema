package com.cinema;

import com.cinema.exception.AuthenticationException;
import com.cinema.lib.Injector;
import com.cinema.model.CinemaHall;
import com.cinema.model.Movie;
import com.cinema.model.MovieSession;
import com.cinema.model.User;
import com.cinema.service.AuthenticationService;
import com.cinema.service.CinemaHallService;
import com.cinema.service.MovieService;
import com.cinema.service.MovieSessionService;
import com.cinema.service.ShoppingCartService;
import com.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.cinema");

    public static void main(String[] args) throws AuthenticationException {
        Movie firstMovie = new Movie();
        firstMovie.setTitle("Fast and Furious");
        firstMovie.setDescription("18+");

        Movie secondMovie = new Movie();
        secondMovie.setTitle("How to Train Your Dragon");
        secondMovie.setDescription("Cartoon");

        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(firstMovie);
        movieService.add(secondMovie);
        System.out.println("Created movies:");
        movieService.getAll().forEach(System.out::println);

        CinemaHall redHall = new CinemaHall(50, "red hall");
        CinemaHall vipHall = new CinemaHall(10, "vip hall");
        CinemaHallService cinemaHallService
                = (CinemaHallService) injector.getInstance(CinemaHallService.class);

        cinemaHallService.add(redHall);
        cinemaHallService.add(vipHall);
        System.out.println("\nCreated cinema halls:");
        cinemaHallService.getAll().forEach(System.out::println);

        MovieSessionService movieSessionService
                = (MovieSessionService) injector.getInstance(MovieSessionService.class);

        MovieSession todayMorningSession = new MovieSession(secondMovie, redHall,
                LocalDateTime.now());
        MovieSession todayEveningSession = new MovieSession(firstMovie, vipHall,
                LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 15)));
        MovieSession tomorrowSession = new MovieSession(secondMovie, redHall,
                LocalDateTime.now().plusDays(1));

        movieSessionService.add(todayMorningSession);
        movieSessionService.add(todayEveningSession);
        movieSessionService.add(tomorrowSession);

        System.out.println("\nToday movie sessions for movie: " + firstMovie);
        System.out.println(movieSessionService
                .findAvailableSessions(firstMovie.getId(), LocalDate.now()));

        System.out.println("\nToday movie sessions for movie: " + secondMovie);
        System.out.println(movieSessionService
                .findAvailableSessions(secondMovie.getId(), LocalDate.now()));

        UserService userService = (UserService) injector.getInstance(UserService.class);

        User userBob = new User();
        userBob.setEmail("bob@gmail.com");
        userBob.setPassword("bob's_Pass");
        userService.add(userBob);

        User userAlice = new User();
        userAlice.setEmail("alice@gmail.com");
        userAlice.setPassword("alice's_pass");
        userService.add(userAlice);

        System.out.println("User by email: " + userBob.getEmail());
        System.out.println(userService.findByEmail("bob@gmail.com"));

        AuthenticationService authenticationService =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);

        User registeredUser = authenticationService
                .register("newUserEmail@mail.com", "!pass_*_pass!");

        System.out.println("Trying to login user:");
        System.out.println(authenticationService
                .login(registeredUser.getEmail(), "!pass_*_pass!"));

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        User executedRegisteredUser = userService.findByEmail("newUserEmail@mail.com").get();
        shoppingCartService.addSession(todayMorningSession, executedRegisteredUser);
        shoppingCartService.addSession(todayEveningSession, executedRegisteredUser);
        System.out.println("Get new user's shopping cart: ");
        System.out.println(shoppingCartService.getByUser(executedRegisteredUser));
        shoppingCartService.clear(shoppingCartService.getByUser(executedRegisteredUser));
        System.out.println("New user's shopping cart after clearing: ");
        System.out.println(shoppingCartService.getByUser(executedRegisteredUser));
    }
}
