package com.diskchop.controller;

import com.diskchop.view.*;

import javax.swing.*;

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
        boolean isSameSubMenu = aberto == subMenu;
        subMenu.setVisible(!isSameSubMenu);
        telaInicialMenu.setSubmenuAberto(isSameSubMenu ? null : subMenu);
    }

    public void configureActions() {
        telaInicialMenu.getBtnMenu().addActionListener(e -> toggleMenu());
        telaInicialMenu.getBtnCadastro().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuCadastro()));
        telaInicialMenu.getBtnEstoque().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuEstoque()));
        telaInicialMenu.getBtnPedido().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuPedido()));
        telaInicialMenu.getBtnFinanceiro().addActionListener(e -> toggleSubMenu(telaInicialMenu.getPainelSubmenuFinanceiro()));
        telaInicialMenu.getBtnCadastroCliente().addActionListener(e -> {
            openCadastroClientes2();
        });
        telaInicialMenu.getBtnAjuda().addActionListener(e -> fecharSubmenuAberto());
        telaInicialMenu.getBtnSair().addActionListener(e -> confirmarSaida());
        telaInicialMenu.getBtnEstoqueBarris().addActionListener(e -> openEstoque());
        telaInicialMenu.getBtnPedidoRegistrar().addActionListener(e -> {openNovoPedido();});
    }

    private void openNovoPedido() {
       NovoPedido novoPedido = new NovoPedido(telaInicialMenu, true);
       NovoPedidoController controller = new NovoPedidoController(novoPedido);
       novoPedido.setVisible(true);
    }

    private void openCadastroClientes() {
        CadastroClientes cadastroClientes = new CadastroClientes(telaInicialMenu, true);
       // CadastroController controller = new CadastroController(cadastroClientes);
        cadastroClientes.setVisible(true);
    }
    private void openCadastroClientes2() {
        Cadastro cadastro = new Cadastro(telaInicialMenu, true);
        CadastroController controller = new CadastroController(cadastro);
        cadastro.setVisible(true);
    }

    private void openEstoque() {
        Estoque estoque = new Estoque(telaInicialMenu, true);
        EstoqueController controller = new EstoqueController(estoque);
        estoque.setVisible(true);
    }

    public void fecharSubmenuAberto() {
        if (telaInicialMenu.getSubmenuAberto() != null) {
            telaInicialMenu.getSubmenuAberto().setVisible(false);
            telaInicialMenu.setSubmenuAberto(null);
        }
    }

    public void confirmarSaida() {
        int resposta = JOptionPane.showConfirmDialog(null,
                "Deseja sair?",
                "Confirmar Saída",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

}
