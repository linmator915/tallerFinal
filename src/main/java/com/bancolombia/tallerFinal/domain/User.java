package com.bancolombia.tallerFinal.domain;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {

    @Id
    private Long userId;

    @Column("nombre")
    @NotNull(message = "El nombre es requerido")
    private String nombre;

    @Column("balance")
    @NotNull(message = "El balance es requerido")
    private Double balance;

    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + userId +
                ", nombre='" + nombre + '\'' +
                ", balance='" + balance + '\'' +
                '}';
    }
}
