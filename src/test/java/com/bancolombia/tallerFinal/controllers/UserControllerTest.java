package com.bancolombia.tallerFinal.controllers;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.domain.User;
import com.bancolombia.tallerFinal.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserController.class, UserService.class, GlobalHandleError.class})
@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    UserService userService;

    @InjectMocks
    private UserController userController;

    /*@BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(userController).build();
    }*/

    @Test
    void crearUser() {

        User userInput = new User();
        userInput.setNombre("Pepita Perez");
        userInput.setBalance(300.50);

        User userOutput = new User();
        userOutput.setId(1L);
        userOutput.setNombre("Pepita Perez");
        userOutput.setBalance(300.50);

        when(userService.crearUser(userInput)).thenReturn(Mono.just(userOutput));

        webTestClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userInput)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class);
                //.isEqualTo(userOutput);
    }

    @Test
    void obtenerPorId(){

        Long userId = 1L;
        User user = new User();
        user.setId(1L);
        user.setNombre("Luis Perez");
        user.setBalance(300.50);

        when(userService.obtenerPorId(userId)).thenReturn(Mono.just(user));

        webTestClient.get()
                .uri("/users/{id}", user.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class)
                .value(userResponse -> assertEquals(user.getId(), userResponse.getId()));
    }

    @Test
    void updateUserBalance(){

        User user = new User();
        user.setId(1L);
        user.setNombre("Pepita Perez");
        user.setBalance(300.50);

        Cashout cashout = new Cashout();
        cashout.setAmount(230.50);

        User userUpdate = new User();
        user.setId(1L);
        user.setNombre("Pepita Perez");
        user.setBalance(230.50);

        when(userService.updateBalance(user.getId(), cashout)).thenReturn(Mono.just(userUpdate));

        webTestClient.put()
                .uri("/users/{id}/balance", user.getId())
                .bodyValue(cashout)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class);
                //.value(userResponse -> assertEquals(user.getId(), userResponse.getId()));

    }
}
