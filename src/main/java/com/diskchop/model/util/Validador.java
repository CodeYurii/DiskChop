package com.diskchop.model.util;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Regime;
import com.diskchop.model.util.erros.ClienteInvalidoException;

import javax.swing.*;

public class Validador {

    public Validador() {}

    public void validarCliente(Cliente cliente) {
        validarNome(cliente.getNome());
        if (cliente.getRegime() == Regime.PESSOA_FISICA) {
            validarCPF(cliente.getCpf());
        } else {
            validarCNPJ(cliente.getCpf());
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty() || !nome.matches("^[\\\\p{L} ]+$")) {
            throw new ClienteInvalidoException(MensagensSistema.CLIENTE_NOME_INVALIDO);
        }
    }

    private void validarCPF(String cpf) {
        if (cpf != null && !cpf.matches("^\\d{11}$")) {
            throw new ClienteInvalidoException(MensagensSistema.CLIENTE_CPF_INVALIDO);
        }
    }

    private void validarCNPJ(String cnpj) {
        if (cnpj != null && !cnpj.matches("^\\d{14}$")) {
            throw new ClienteInvalidoException(MensagensSistema.CLIENTE_CNPJ_INVALIDO);
        }
    }
}
