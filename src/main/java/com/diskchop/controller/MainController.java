package com.diskchop.controller;

import com.diskchop.view.CadastroClientes;
import com.diskchop.view.TelaInicialMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MainController {

    private TelaInicialMenu telaInicialMenu;

    public MainController() {
        initViews();
    }

    private void initViews() {
        // Inicializa a tela inicial
        telaInicialMenu = new TelaInicialMenu();

        // Configura as ações dos botões principais
        configureActions();
    }

    private void configureActions() {
        // Configura o botão de cadastro de clientes


        //telaInicial.addCadastroButtonListener(e -> openCadastroClientes());

        // Configura outros botões aqui...
        // Exemplo: telaInicial.addEstoqueButtonListener(e -> openEstoqueView());
    }

    private void openCadastroClientes() {
        // Abre a tela de CadastroClientes como modal
        CadastroClientes cadastroClientes = new CadastroClientes(telaInicialMenu, true);
        cadastroClientes.setVisible(true);
    }

    public void abreFechaMenu() {
        boolean isMenuVisible = telaInicialMenu.getMenuLateral().isVisible();
        telaInicialMenu.getMenuLateral().setVisible(!isMenuVisible);
    }


    public void abreFechaSubMenu(JMenu submenu) {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainController::new);
    }
}
