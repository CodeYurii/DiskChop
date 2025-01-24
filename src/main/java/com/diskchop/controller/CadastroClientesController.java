package com.diskchop.controller;

import com.diskchop.model.dao.ClienteDao;
import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Regime;
import com.diskchop.view.CadastroClientes;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.diskchop.model.entity.Regime.PESSOA_FISICA;
import static com.diskchop.model.entity.Regime.PESSOA_JURIDICA;

public class CadastroClientesController {
    private CadastroClientes view;
    private Cliente cliente;
    private ClienteDao clienteDao;// Model

    public CadastroClientesController(CadastroClientes view) {
        this.view = view;
        this.cliente = new Cliente();
        this.clienteDao = new ClienteDao();
        configureActions();
    }

    public void configureActions(){
        view.getBotaoCadVoltar().addActionListener(e -> {voltarMenu();});
        view.getBotaoCadClienteAdicionar().addActionListener(e -> {salvarCliente();});
    }
    public void voltarMenu(){
        view.dispose();
    }

    // Lógica para salvar o cliente
    private void salvarCliente() {
        // Captura os dados da View
        cliente.setNome(view.getTextFieldCadNome());
        cliente.setCpf(view.getTextFieldCadCpf());
        cliente.setObservacao(view.getTextFieldCadObservacaoCliente());
        cliente.setRegime(view.getComboCadPessoa());
        cliente.setStatus(true);
        if (cliente.getDataCadastroCliente() == null) {
            cliente.setDataCadastroCliente();
        }
        clienteDao.salvar(cliente);
        JOptionPane.showOptionDialog(null, "Cliente cadastrado com sucesso!", "Sucesso",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] { "OK" }, "OK");
    }
    // Lógica para fechar a tela
    private void fecharTela() {
        view.dispose();
    }

    /*// Método para preencher os campos no caso de edição
    public void preencherCampos(Cliente cliente) {
        this.cliente = cliente;
        view.setNome(cliente.getNome());
        view.setEmail(cliente.getEmail());
        view.setTelefone(cliente.getTelefone());
    }*/
}
