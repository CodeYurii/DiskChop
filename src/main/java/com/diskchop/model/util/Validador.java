package com.diskchop.model.util;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import com.diskchop.model.entity.Regime;
import com.diskchop.model.entity.Telefone;
import com.diskchop.model.util.erros.ClienteInvalidoException;
import com.diskchop.model.util.erros.EnderecoInvalidoException;
import com.diskchop.model.util.erros.TelefoneInvalidoException;

import javax.swing.*;

public class Validador {

    public Validador() {}


    /*** Validação Telefone ***/
    public void validarTelefone(Telefone telefone) throws TelefoneInvalidoException {
        validarNumeroTelefone(telefone.getTelefone());
        validarContatoTelefone(telefone.getContato());
    }

    private void validarNumeroTelefone(String telefone){
        if (telefone != null && !telefone.matches("^\\d{11}$")) {
            throw new ClienteInvalidoException(MensagensSistema.TELEFONE_NUMERO_INVALIDO);
        }
    }

    private void validarContatoTelefone(String contato){
        if(contato == null || !contato.matches("^[\\p{L} .'-]+$")){
            throw new EnderecoInvalidoException(MensagensSistema.TELEFONE_NUMERO_INVALIDO);
        }
    }

    /*** Validação Endereço ***/
    public void validarEndereco(Endereco endereco) throws EnderecoInvalidoException {
        validarLogradouro(endereco.getLogradouro());
        validarNumero(endereco.getNumero());
        validarCidade(endereco.getCidade());
        validarBairro(endereco.getBairro());
        validarCep(endereco.getCep());
    }
    private void validarLogradouro(String logradouro){
        if(logradouro == null || !logradouro.matches("^[\\p{L} .'-]+$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_LOGRADOURO_INVALIDO);
        }
    }
    private void validarNumero(String numero){
        if(numero == null || !numero.matches("[A-Za-z0-9 ]+$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_NUMERO_INVALIDO);
        }
    }
    private void validarCep(String cep){
        if(cep == null || !cep.matches("^\\d{8}$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_CEP_INVALIDO);
        }
    }
    private void validarBairro(String bairro){
        if(bairro == null || !bairro.matches("^[\\p{L} .'-]+$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_BAIRRO_INVALIDO);
        }
    }
    private void validarCidade(String cidade){
        if(cidade == null || !cidade.matches("^[\\p{L} .'-]+$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_CIDADE_INVALIDO);
        }
    }


    /*** Validação Cliente ***/
    public void validarCliente(Cliente cliente) {
        validarNome(cliente.getNome());
        if (cliente.getRegime() == Regime.PESSOA_FISICA) {
            validarCPF(cliente.getCpf());
        } else {
            validarCNPJ(cliente.getCpf());
        }
    }
    private void validarNome (String nome){
        if (nome == null || nome.trim().isEmpty() || !nome.matches("^[\\p{L} .'-]+$")) {
            throw new ClienteInvalidoException(MensagensSistema.CLIENTE_NOME_INVALIDO);
        }
    }

    private void validarCPF (String cpf){
        if (cpf != null && !cpf.matches("^\\d{11}$")) {
            throw new ClienteInvalidoException(MensagensSistema.CLIENTE_CPF_INVALIDO);
        }
    }

    private void validarCNPJ (String cnpj){
        if (cnpj != null && !cnpj.matches("^\\d{14}$")) {
            throw new ClienteInvalidoException(MensagensSistema.CLIENTE_CNPJ_INVALIDO);
        }
    }

    }
