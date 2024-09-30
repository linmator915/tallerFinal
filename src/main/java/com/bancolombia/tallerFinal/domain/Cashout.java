package com.bancolombia.tallerFinal.domain;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("cashouts")
public class Cashout {

    @Id
    private Long id;

    @Column("userId")
    @NotNull(message = "El id del usuario es requerido")
    private Long userId;

    @Column("amount")
    @NotNull(message = "El monto del retiro es requerido")
    private Double amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Cashout{" +
                "id=" + id +
                ", userId=" + userId +
                ", amount='" + amount + '\'' +
                '}';
    }
}
