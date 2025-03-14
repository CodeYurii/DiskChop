package com.diskchop.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Maquina extends Produto{
    public Maquina(){}

    public Maquina(String nome, Integer quantidade, Double preco, StatusProduto statusProduto) {
        super(nome, quantidade, preco, statusProduto);
    }
}
