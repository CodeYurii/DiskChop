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

    @ManyToOne(fetch = FetchType.EAGER)
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
    private String cep;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private Tipo tipo;
    @Column
    private String observacaoEndereco;
    @Column(name = "data_cadastro_endereco", nullable = false, updatable = false)
    private LocalDateTime dataCadastroEndereco;

    public Endereco() {
    }

    public Endereco(Long idEndereco, Cliente cliente, String logradouro, String numero, String complemento, String bairro,
                    String cidade, String cep, String observacaoEndereco) {
        this.idEndereco = idEndereco;
        this.cliente = cliente;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.observacaoEndereco = observacaoEndereco;
        this.dataCadastroEndereco = LocalDateTime.now();
    }

    public Endereco(Long idEndereco, Cliente cliente, String logradouro, String numero, String complemento, String bairro,
                    String cidade, String cep, String observacaoEndereco, LocalDateTime dataCadastroEndereco) {
        this.idEndereco = idEndereco;
        this.cliente = cliente;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.observacaoEndereco = observacaoEndereco;
        this.dataCadastroEndereco = dataCadastroEndereco != null ? dataCadastroEndereco : LocalDateTime.now();
    }

    public Endereco(Long idEndereco, Cliente cliente, String logradouro, String numero, String complemento, String bairro, String cidade,
                    String cep, Tipo tipo, String observacaoEndereco, LocalDateTime dataCadastroEndereco) {
        this.idEndereco = idEndereco;
        this.cliente = cliente;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.cep = cep;
        this.tipo = tipo;
        this.observacaoEndereco = observacaoEndereco;
        this.dataCadastroEndereco = dataCadastroEndereco != null ? dataCadastroEndereco : LocalDateTime.now();;
    }

    public void setDataCadastroEndereco() {this.dataCadastroEndereco = LocalDateTime.now();}
}
