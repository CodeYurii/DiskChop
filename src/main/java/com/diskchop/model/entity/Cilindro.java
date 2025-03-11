package com.diskchop.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("CILINDRO")
public class Cilindro extends Produto{
    public Cilindro(){}

    public Cilindro(String nome, Integer quantidade, Double preco, StatusProduto statusProduto) {
        super(nome, quantidade, preco, statusProduto);
    }
}
