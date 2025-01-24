package com.diskchop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEndereco;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String logradouro;
    @Column(nullable = false)
    private String numero;
    @Column()
    private String complemento;
    @Column(nullable = false)
    private String bairro;
    @Column(nullable = false)
    private String cidade;
    @Column(nullable = false)
    private String uf;
    @Column(nullable = false)
    private String cep;
    @Column
    private String observacaoEndereco;
    @Column(name = "data_cadastro_endereco", nullable = false, updatable = false)
    private LocalDateTime dataCadastroEndereco;

    public Endereco() {
    }

    public Endereco(Long idEndereco, Cliente cliente, String logradouro, String numero, String complemento, String bairro,
                    String uf, String cidade, String cep, String observacaoEndereco) {
        this.idEndereco = idEndereco;
        this.cliente = cliente;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.uf = uf;
        this.cidade = cidade;
        this.cep = cep;
        this.observacaoEndereco = observacaoEndereco;
        this.dataCadastroEndereco = LocalDateTime.now();
    }

    public Endereco(Long idEndereco, Cliente cliente, String logradouro, String numero, String complemento, String bairro,
                    String uf, String cidade, String cep, String observacaoEndereco, LocalDateTime dataCadastroEndereco) {
        this.idEndereco = idEndereco;
        this.cliente = cliente;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.uf = uf;
        this.cidade = cidade;
        this.cep = cep;
        this.observacaoEndereco = observacaoEndereco;
        this.dataCadastroEndereco = dataCadastroEndereco != null ? dataCadastroEndereco : LocalDateTime.now();
    }
}
