package com.bancolombia.tallerFinal.services;

import com.bancolombia.tallerFinal.domain.Cashout;
import com.bancolombia.tallerFinal.domain.User;
import com.bancolombia.tallerFinal.domain.repositories.UserRepository;
import com.bancolombia.tallerFinal.exceptions.BalanceException;
import com.bancolombia.tallerFinal.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService{

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> crearUser(User user){return userRepository.save(user);}

    public Mono<User> obtenerPorId(Long id) {
        return userRepository.findByUserId(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Usuario no existe"))) ;
    }

    public Mono<String> validarBalance(Long id, Cashout amount) {
        return userRepository.findByUserId(id)
            .flatMap(user -> {
                if (user.getBalance() < amount.getAmount()) {
                    return Mono.error(new BalanceException("Balance insuficiente"));
                }
                return Mono.just("OK");
            })
            .onErrorResume(throwable -> {
                if (throwable instanceof BalanceException) {
                    return Mono.just(throwable.getMessage());
                }
                return Mono.just("Error al validar el balance");
            });
    }

    public Mono<User> updateBalance(Long id, Cashout amount) {
        return userRepository.findByUserId(id)
            .flatMap(user -> {
                user.setBalance(user.getBalance() - amount.getAmount());
                return userRepository.save(user);
            })
            .onErrorResume(throwable -> {
                System.err.println("Error: " + throwable.getMessage());
                return Mono.error(throwable);
            });
    }
}
