package com.diskchop.controller;

import com.diskchop.view.CadastroClientes;
import com.diskchop.view.TelaInicialMenu;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController {
    private TelaInicialMenu telaInicialMenu;

    public MainController() {
    }

    public MainController(TelaInicialMenu telaInicialMenu) {
        this.telaInicialMenu = telaInicialMenu;
       // configureActions();
    }

    public void toggleMenu() {
        boolean menuAberto = telaInicialMenu.isMenuAberto();
        menuAberto = !menuAberto;
        telaInicialMenu.setMenuAberto(menuAberto);
        telaInicialMenu.getMenuLateral().setVisible(menuAberto);
        telaInicialMenu.getMenuLateral().revalidate();
        telaInicialMenu.getMenuLateral().repaint();

        System.out.println("Menu aberto: " + menuAberto);
        System.out.println("Menu visível: " + telaInicialMenu.getMenuLateral().isVisible());

    }

    private void initViews() {
        // Inicializa a tela inicial
        //telaInicialMenu = new TelaInicialMenu();

        // Configura as ações dos botões principais
        configureActions();
    }

    public void configureActions() {
        // Configura o botão de cadastro de clientes
        telaInicialMenu.getBtnMenu().addActionListener(e -> toggleMenu());
        //telaInicial.addCadastroButtonListener(e -> openCadastroClientes());

    }

    private void openCadastroClientes() {
        // Abre a tela de CadastroClientes como modal
        CadastroClientes cadastroClientes = new CadastroClientes(telaInicialMenu, true);
        cadastroClientes.setVisible(true);
    }




    public void abreFechaSubMenu(JMenu submenu) {

    }


}
