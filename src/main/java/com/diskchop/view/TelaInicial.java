package com.diskchop.view;

import javax.swing.*;
import java.awt.*;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        // Configurações da janela principal
        setTitle("Sistema de Gestão");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(80, 200, 120)); // Verde escuro agradável

        // Criação da barra de menu
        JMenuBar menuBar = new JMenuBar();

        // Menus principais
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenu menuCadastro = new JMenu("Cadastro");
        JMenu menuPedido = new JMenu("Pedido");
        JMenu menuEstoque = new JMenu("Estoque");
        JMenu menuFinanceiro = new JMenu("Financeiro");
        JMenu menuAjuda = new JMenu("Ajuda");

        // Submenus
        // Arquivo
        JMenuItem itemSair = new JMenuItem("Sair");
        menuArquivo.add(itemSair);

        // Cadastro
        JMenuItem itemClientes = new JMenuItem("Clientes");
        JMenu menuProdutos = new JMenu("Produtos");
        JMenuItem itemBarris = new JMenuItem("Barris");
        JMenuItem itemMaquinas = new JMenuItem("Máquinas");
        JMenuItem itemCilindros = new JMenuItem("Cilindros");

        menuProdutos.add(itemBarris);
        menuProdutos.add(itemMaquinas);
        menuProdutos.add(itemCilindros);
        menuCadastro.add(itemClientes);
        menuCadastro.add(menuProdutos);

        // Pedido
        JMenuItem itemRegistrarPedido = new JMenuItem("Registrar Pedido");
        JMenuItem itemConsultarPedido = new JMenuItem("Consultar Pedido");
        menuPedido.add(itemRegistrarPedido);
        menuPedido.add(itemConsultarPedido);

        // Estoque
        JMenuItem itemControleBarril = new JMenuItem("Controle de Barris");
        JMenuItem itemControleMaquina = new JMenuItem("Controle de Máquinas");
        JMenuItem itemControleCilindro = new JMenuItem("Controle de Cilindros");
        menuEstoque.add(itemControleBarril);
        menuEstoque.add(itemControleMaquina);
        menuEstoque.add(itemControleCilindro);

        // Financeiro
        JMenuItem itemEntradaSaida = new JMenuItem("Entradas e Saídas");
        JMenuItem itemRelatorioFinanceiro = new JMenuItem("Relatórios Financeiros");
        menuFinanceiro.add(itemEntradaSaida);
        menuFinanceiro.add(itemRelatorioFinanceiro);

        // Ajuda
        JMenuItem itemSobreSistema = new JMenuItem("Sobre o Sistema");
        menuAjuda.add(itemSobreSistema);

        // Adicionando os menus à barra de menu
        menuBar.add(menuArquivo);
        menuBar.add(menuCadastro);
        menuBar.add(menuPedido);
        menuBar.add(menuEstoque);
        menuBar.add(menuFinanceiro);
        menuBar.add(menuAjuda);

        // Adicionando a barra de menu ao JFrame
        setJMenuBar(menuBar);

        // Exibindo a janela
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaInicial();
    }
}
