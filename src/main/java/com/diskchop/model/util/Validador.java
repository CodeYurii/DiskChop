package com.diskchop.model.util;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import com.diskchop.model.entity.Regime;
import com.diskchop.model.util.erros.ClienteInvalidoException;
import com.diskchop.model.util.erros.EnderecoInvalidoException;

import javax.swing.*;

public class Validador {

    public Validador() {}

    /*** Validação Endereço ***/
    public void validarEndereco(Endereco endereco) throws EnderecoInvalidoException {
        System.out.println(endereco.getLogradouro());
        validarLogradouro(endereco.getLogradouro());

        validarNumero(endereco.getNumero());
        validarCidade(endereco.getCidade());
        validarBairro(endereco.getBairro());
        validarCep(endereco.getCep());
        validarUF(endereco.getUf());
        validarObsEndereco(endereco.getObservacaoEndereco());
        validarComplemento(endereco.getComplemento());
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
    private void validarUF(String uf){
        if(uf == null || !uf.matches("^(AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS" +
                "|RO|RR|SC|SP|SE|TO|ac|al|ap|am|ba|ce|df|es|go|ma|mt|ms|mg|pa|pb|pr|pe|pi|rj|rn|rs|ro|rr|sc|sp|se|to)$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_UF_INVALIDO);
        }
    }
    private void validarObsEndereco(String obsEndereco){
        if(obsEndereco == null || !obsEndereco.matches("^[\\p{L} .'-]+$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_OBSERVACAO_INVALIDO);
        }
    }
    private void validarComplemento(String complemento){
        if(complemento == null || !complemento.matches("^[\\p{L} .'-]+$")){
            throw new EnderecoInvalidoException(MensagensSistema.ENDERECO_COMPLEMENTO_INVALIDO);
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
