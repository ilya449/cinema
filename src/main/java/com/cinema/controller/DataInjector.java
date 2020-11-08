package com.cinema.controller;

import com.cinema.model.Role;
import com.cinema.model.User;
import com.cinema.service.RoleService;
import com.cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInjector {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataInjector(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void init() {
        roleService.add(Role.of("ADMIN"));
        roleService.add(Role.of("USER"));
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setPassword("matestudent");
        admin.setRoles(Set.of(roleService.getRoleByName("ADMIN")));
        userService.add(admin);
    }
}
