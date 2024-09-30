package com.bancolombia.tallerFinal.controllers;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.domain.User;
import com.bancolombia.tallerFinal.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<User> crearUser(@Valid @RequestBody User user) {
        return userService.crearUser(user);
    }

    @GetMapping("/{id}")
    public Mono<User> obtenerPorId(@PathVariable("id") Long id){
        return userService.obtenerPorId(id);
    }

    @PutMapping("/{id}/balance")
    public Mono<User> updateUserBalance(@PathVariable("id") Long userId, @RequestBody Cashout amount){
        return userService.updateBalance(userId, amount);
    }

}
