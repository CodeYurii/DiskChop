package com.diskchop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Barril extends Produto{

    @Temporal(TemporalType.DATE)
    private Date validade;

    public Barril() {}

    public Barril(String nome, Integer quantidade, Double preco, StatusProduto statusProduto, Date validade) {
        super(nome, quantidade, preco, statusProduto);
        this.validade = validade;
    }
}
