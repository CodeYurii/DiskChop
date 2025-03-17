package com.diskchop.model.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@Table(name = "tb_pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produto> produtos;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Column(name = "hora_entrega")
    private LocalTime horaEntrega;

    @Column(name = "data_retirada")
    private LocalDate dataRetirada;

    @Column(name = "hora_retirada")
    private LocalTime horaRetirada;

    @Column(name = "endereco_entrega")
    private String enderecoEntrega;

    @Column(name = "endereco_retirada")
    private String enderecoRetirada;

    @Column(name = "forma_pagamento")
    private String formaPagamento;

    @Column(name = "status_pagamento")
    private String statusPagamento;

    @Column(name = "taxa_entrega")
    private double taxaEntrega;

    @Column(name = "desconto")
    private double desconto;

    @Column(name = "valor_total")
    private double valorTotal;

    @Column(name = "observacao")
    private String observacao;
}