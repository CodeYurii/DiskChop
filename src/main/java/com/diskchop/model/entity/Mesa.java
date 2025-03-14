package com.diskchop.model.entity;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data

public class Mesa extends Produto{
    public Mesa(){}

    public Mesa(String nome, Integer quantidade, Double preco, StatusProduto statusProduto) {
        super(nome, quantidade, preco, statusProduto);
    }
}
