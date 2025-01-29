package com.diskchop.model.util;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Regime;
import com.diskchop.model.util.erros.ClienteInvalidoException;

public class Validador {

    public Validador(){}

    public void validarCliente(Cliente cliente) throws ClienteInvalidoException {
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty() ||
                !cliente.getNome().matches("^[a-zA-Z ]+$")) {
           throw new IllegalArgumentException(MensagensSistema.CLIENTE_NOME_INVALIDO);
        }
        if (cliente.getRegime() == Regime.PESSOA_FISICA) {
            if (cliente.getCpf() != null && !cliente.getCpf().matches("^^\\d{11}$")) {
                throw new IllegalArgumentException(MensagensSistema.CLIENTE_CPF_INVALIDO);
            }
        } else {
            if (cliente.getCpf() != null && !cliente.getCpf().matches("^\\d{14}$")) {
                throw new IllegalArgumentException(MensagensSistema.CLIENTE_CNPJ_INVALIDO);
            }
        }
    }
}
