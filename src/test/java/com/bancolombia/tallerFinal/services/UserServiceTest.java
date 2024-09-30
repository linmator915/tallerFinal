package com.bancolombia.tallerFinal.services;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.domain.User;
import com.bancolombia.tallerFinal.domain.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testcrearUser(){

        User userInput = new User();
        userInput.setNombre("Pepita Perez");
        userInput.setBalance(300.50);

        User userOutput = new User();
        userOutput.setId(1L);
        userOutput.setNombre("Pepita Perez");
        userOutput.setBalance(300.50);

        when(userRepository.save(userInput)).thenReturn(Mono.just(userOutput));

        StepVerifier.create(userService.crearUser(userInput))
                .expectNext(userOutput)
                .verifyComplete();
    }

    @Test
    public void testObtenerPorId() {

        User user = new User();
        user.setId(1L);
        user.setNombre("Luis Perez");
        user.setBalance(300.50);

        when(userRepository.findByUserId(user.getId())).thenReturn(Mono.just(user));

        StepVerifier.create(userService.obtenerPorId(1L))
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    public void testValidarBalance(){

        User userInput = new User();
        userInput.setId(1L);
        userInput.setNombre("Pepita Perez");
        userInput.setBalance(300.50);

        Cashout cashoutInput = new Cashout();
        cashoutInput.setUserId(1L);
        cashoutInput.setAmount(100.0);

        when(userRepository.findByUserId(userInput.getId())).thenReturn(Mono.just(userInput));

        StepVerifier.create(userService.validarBalance(userInput.getId(), cashoutInput))
                .expectNext("OK")
                .verifyComplete();
    }

    @Test
    public void testupdateBalance(){

        User userInput = new User();
        userInput.setId(1L);
        userInput.setNombre("Pepita Perez");
        userInput.setBalance(300.50);

        Cashout cashoutInput = new Cashout();
        cashoutInput.setUserId(1L);
        cashoutInput.setAmount(100.0);

        User userOutput = new User();
        userInput.setId(1L);
        userInput.setNombre("Pepita Perez");
        userInput.setBalance(200.50);

        when(userRepository.findByUserId(userInput.getId())).thenReturn(Mono.just(userInput));
        when(userRepository.save(userInput)).thenReturn(Mono.just(userOutput));

        StepVerifier.create(userService.updateBalance(userInput.getId(), cashoutInput))
                .expectNext(userOutput)
                .verifyComplete();
    }
}
