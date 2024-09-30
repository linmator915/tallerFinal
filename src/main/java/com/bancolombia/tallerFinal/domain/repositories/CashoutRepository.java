package com.bancolombia.tallerFinal.domain.repositories;

import com.bancolombia.tallerFinal.domain.Cashout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CashoutRepository extends ReactiveCrudRepository<Cashout, Long> {
    Flux<Cashout> findByUserId(Long id);
}
