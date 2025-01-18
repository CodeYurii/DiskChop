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
    }

    public void toggleMenu() {
        boolean menuAberto = telaInicialMenu.isMenuAberto();
        menuAberto = !menuAberto;
        telaInicialMenu.setMenuAberto(menuAberto);
        telaInicialMenu.getMenuLateral().setVisible(menuAberto);
        telaInicialMenu.getMenuLateral().revalidate();
        telaInicialMenu.getMenuLateral().repaint();
    }

    public void toggleSubMenu(JPanel subMenu){
        JPanel aberto = telaInicialMenu.getSubmenuAberto();
        if (aberto != null) aberto.setVisible(false);
        // Verifica se o submenu clicado é o que já está aberto
        boolean isSameSubMenu = aberto == subMenu;
        subMenu.setVisible(!isSameSubMenu); // Se for o mesmo, fecha, caso contrário, abre
        telaInicialMenu.setSubmenuAberto(isSameSubMenu ? null : subMenu); // Atualiza o submenu aberto
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
        //JPanel painelSubmenuCadastro = telaInicialMenu.getPainelSubmenuCadastro();
        telaInicialMenu.getBtnCadastro().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuCadastro()));
        telaInicialMenu.getBtnEstoque().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuEstoque()));
        telaInicialMenu.getBtnPedido().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuPedido()));
        telaInicialMenu.getBtnFinanceiro().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuFinanceiro()));
        telaInicialMenu.getBtnCadastroCliente().addActionListener(e -> {
            openCadastroClientes();
        });
        telaInicialMenu.getBtnAjuda().addActionListener(e -> fecharSubmenuAberto());
        telaInicialMenu.getBtnSair().addActionListener(e -> confirmarSaida());
    }

    private void openCadastroClientes() {
        // Abre a tela de CadastroClientes como modal
        CadastroClientes cadastroClientes = new CadastroClientes(telaInicialMenu, true);
        cadastroClientes.setVisible(true);
    }

    public void fecharSubmenuAberto() {
        if (telaInicialMenu.getSubmenuAberto() != null) {
            telaInicialMenu.getSubmenuAberto().setVisible(false); // Fecha o submenu aberto
            telaInicialMenu.setSubmenuAberto(null); // Nenhum submenu está aberto
        }
    }

    public void confirmarSaida() {
        int resposta = JOptionPane.showConfirmDialog(null,
                "Deseja sair?",
                "Confirmar Saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Se o usuário clicar em "Sim", fecha o programa
        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0); // Fecha o programa
        }
    }

}
