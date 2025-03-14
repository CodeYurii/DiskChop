package com.diskchop.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Diverso extends Produto{
    public Diverso() {
    }

    public Diverso(String nome, Integer quantidade, Double preco, StatusProduto statusProduto) {
        super(nome, quantidade, preco, statusProduto);
    }
}
