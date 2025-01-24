package com.diskchop.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_telefones")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTelefone;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private String numero;

    @Column
    private String contato;

    @Column(name = "data_cadastro_telefone", nullable = false, updatable = false)
    private LocalDateTime dataCadastroTelefone;

    public Telefone() {
    }

    public Telefone(Long idTelefone, Cliente cliente, String numero, String contato) {
        this.idTelefone = idTelefone;
        this.cliente = cliente;
        this.numero = numero;
        this.contato = contato;
        this.dataCadastroTelefone = LocalDateTime.now();
    }

    public Telefone(Long idTelefone, Cliente cliente, String numero, String contato, LocalDateTime dataCadastroTelefone) {
        this.idTelefone = idTelefone;
        this.cliente = cliente;
        this.numero = numero;
        this.contato = contato;
        this.dataCadastroTelefone = dataCadastroTelefone != null ? dataCadastroTelefone : LocalDateTime.now();
    }
}
