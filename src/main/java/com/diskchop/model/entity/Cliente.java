package com.diskchop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Enumerated(EnumType.STRING)
    @Column(name = "regime", nullable = true)
    private Regime regime;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "data_cadastro_cliente", nullable = false, updatable = false)
    private LocalDateTime dataCadastroCliente;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_cliente", nullable = true)
    private StatusCliente statusCliente;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("dataCadastroEndereco DESC")
    private List<Endereco> enderecos = new ArrayList<>();

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("dataCadastroTelefone DESC")
    private List<Telefone> telefones = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String nome, String cpf, Regime regime, String observacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.regime = regime;
        this.observacao = observacao;
    }

    public Cliente(Long idCliente, String nome, String cpf, Regime regime, String observacao, LocalDateTime dataCadastroCliente,
                   StatusCliente statusCliente, List<Endereco> enderecos, List<Telefone> telefones) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.regime = regime;
        this.observacao = observacao;
        this.dataCadastroCliente = (dataCadastroCliente != null) ? dataCadastroCliente : LocalDateTime.now();
        this.statusCliente = statusCliente;
        this.enderecos = enderecos;
        this.telefones = telefones;
    }

    public void setDataCadastroCliente() {
        this.dataCadastroCliente = LocalDateTime.now();
}

}
