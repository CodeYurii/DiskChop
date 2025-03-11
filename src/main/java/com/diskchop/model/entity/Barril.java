package com.diskchop.model.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@DiscriminatorValue("BARRIL")
public class Barril extends Produto{

    @Temporal(TemporalType.DATE)
    private Date validade;

    public Barril() {}

    public Barril(String nome, Integer quantidade, Double preco, StatusProduto statusProduto, Date validade) {
        super(nome, quantidade, preco, statusProduto);
        this.validade = validade;
    }
}
