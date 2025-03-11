package com.diskchop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // Estratégia para herança no JPA
@DiscriminatorColumn(name = "categoria", discriminatorType = DiscriminatorType.STRING)
public abstract class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private Integer quantidade;
    private Double preco;
    @Enumerated(EnumType.STRING)
    private StatusProduto statusProduto;

    public Produto(){}

    public Produto(String nome, Integer quantidade, Double preco, StatusProduto statusProduto) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.statusProduto = statusProduto;
    }

    public Produto(Long id, String nome, Integer quantidade, Double preco, StatusProduto statusProduto) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.statusProduto = statusProduto;
    }
}
