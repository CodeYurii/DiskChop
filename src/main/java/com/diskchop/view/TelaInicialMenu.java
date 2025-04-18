package com.diskchop.view;
import com.diskchop.controller.CarregadorIcones;

import javax.swing.*;
import java.awt.*;

public class TelaInicialMenu extends JFrame {

    private JPanel menuLateral; // Painel do menu lateral
    private boolean menuAberto = false; // Estado do menu (aberto ou fechado)
    private JPanel painelSubmenuCadastro, painelSubmenuEstoque, painelSubmenuPedido, painelSubmenuFinanceiro;
    private JPanel submenuAberto = null;
    private JButton btnCadastro, btnPedido, btnMenu, btnEstoque, btnFinanceiro, btnAjuda, btnSair, btnCadastroCliente,
    btnCadastroProduto, btnEstoqueBarris, btnEstoqueChoppeiras, btnEstoqueCilindros, btnPedidoRegistrar, btnPedidoConsultar,
    btnFinanceiroEntradas, btnFinanceiroResumo;


    public TelaInicialMenu() {
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Configurações da janela principal
        setTitle("Software");
        setLayout(new BorderLayout()); // Ou outro layout que se adeque melhor ao seu design
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiza a janela na inicialização
        setUndecorated(true); // Se não quiser a barra de título, use setUndecorated(true)
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela (opcional)
        setVisible(true);

        // Botão do menu
        btnMenu = new JButton(CarregadorIcones.loadIcon("/icones/menu2.png",32,32));
        btnMenu.setMargin(new Insets(5, 5, 5, 5));
        btnMenu.setFocusPainted(false);
        btnMenu.setBorderPainted(false);
        btnMenu.setBackground(new Color(18, 18, 18));
        btnMenu.setForeground(Color.WHITE);

        // Cabeçalho Superior
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(18, 18, 18));
        cabecalho.setPreferredSize(new Dimension(getWidth(), 60));
        cabecalho.setLayout(new BorderLayout());
        cabecalho.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50)));
        JLabel titulo = new JLabel("DISK CHOPP SUZANO", SwingConstants.CENTER);
        titulo.setFont(new Font("Roboto", Font.ITALIC | Font.BOLD, 40));
        titulo.setForeground(new Color(217, 231, 196));
        cabecalho.add(btnMenu, BorderLayout.WEST);
        cabecalho.add(titulo, BorderLayout.CENTER);

        // Menu Lateral
        menuLateral = new JPanel();
        menuLateral.setBackground(new Color(40, 40, 40));
        menuLateral.setPreferredSize(new Dimension(180, getHeight()));
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS));
        menuLateral.setVisible(false);

        //Botões Menu
        btnCadastro = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/cadastro.png",32, 32), "Cadastro");
        btnPedido = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/pedido3.png",32, 32), "Pedido");
        btnEstoque = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/estoqueBarril9.png",32,32), "Estoque");
        btnFinanceiro = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/financeiro4.png",32,32), "Financeiro");
        btnAjuda = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/ajuda2.png",32,32), "Ajuda");
        btnSair = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/sair5.png",32,32), "Sair");

        // Criar submenus
        btnCadastroCliente = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/cadastroUsuario3.png",32,32), "Cliente");
        btnCadastroProduto = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/cadastroProduto2.png",32,32), "Produto");
        painelSubmenuCadastro = criarPainelSubMenu(btnCadastroCliente);
        painelSubmenuCadastro.add(btnCadastroProduto);

        btnEstoqueBarris = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/estoqueBarril10.png",32,32), "Estoque Barris");
        btnEstoqueChoppeiras = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/choppeira2.png",32,32), "Estoque Choppeiras");
        btnEstoqueCilindros = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/estoqueCilindro2.png",32,32), "Estoque Cilindros");
        painelSubmenuEstoque = criarPainelSubMenu(btnEstoqueBarris);
        painelSubmenuEstoque.add(btnEstoqueChoppeiras);
        painelSubmenuEstoque.add(btnEstoqueCilindros);

        btnPedidoRegistrar = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/registrarPedido.png",32,32), "Registrar Pedido");
        btnPedidoConsultar = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/consultarPedido.png",32,32), "Consultar Pedido");
        painelSubmenuPedido = criarPainelSubMenu(btnPedidoRegistrar);
        painelSubmenuPedido.add(btnPedidoConsultar);

        btnFinanceiroEntradas = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/entradaSaida2.png",32,32), "Entrada e Saídas");
        btnFinanceiroResumo = criarBotaoSubMenu(CarregadorIcones.loadIcon("/icones/relatorio.png",32,32), "Resumo");
        painelSubmenuFinanceiro = criarPainelSubMenu(btnFinanceiroEntradas);
        painelSubmenuFinanceiro.add(btnFinanceiroResumo);

        // Adiciona os botões e submenus ao painel lateral
        menuLateral.add(btnCadastro);
        menuLateral.add(painelSubmenuCadastro);
        menuLateral.add(btnPedido);
        menuLateral.add(painelSubmenuPedido);
        menuLateral.add(btnEstoque);
        menuLateral.add(painelSubmenuEstoque);
        menuLateral.add(btnFinanceiro);
        menuLateral.add(painelSubmenuFinanceiro);
        menuLateral.add(btnAjuda);
        menuLateral.add(btnSair);

        //Painel Principal
        JPanel painelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Carrega e desenha a imagem de fundo
                Image imagemFundo = new ImageIcon(getClass().getResource("/icones/disk.png")).getImage();
                g.drawImage(imagemFundo, 600, 300, 400, 200, this);
            }
        };
        painelPrincipal.setLayout(new BorderLayout());
        painelPrincipal.setBackground(new Color(18, 18, 18));

        // Adiciona os painéis ao JFrame
        add(cabecalho, BorderLayout.NORTH);
        add(menuLateral, BorderLayout.WEST);
        add(painelPrincipal, BorderLayout.CENTER);
        setVisible(true);
    }

    // Função para criar botões
    private JButton criarBotaoMenu(ImageIcon icone, String titulo) {
        JButton botao = new JButton(icone);
        botao.setBackground(new Color(63, 63, 63));
        botao.setFont(new Font("Roboto", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setForeground(new Color(250,250,250));
        botao.setText(titulo);
        botao.setMaximumSize(new Dimension(180, 60));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setHorizontalTextPosition(SwingConstants.RIGHT);
        botao.setMargin(new Insets(0, 10, 0, 5));
        botao.setIconTextGap(10);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }

    private JButton criarBotaoSubMenu(ImageIcon icone, String descricao) {
        JButton botao = new JButton(icone);
        botao.setText(descricao);
        botao.setBackground(new Color(87, 87, 87));
        botao.setFont(new Font("Roboto", Font.ITALIC, 12));
        botao.setForeground(new Color(250,250,250));
        botao.setMaximumSize(new Dimension(180, 40));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setHorizontalTextPosition(SwingConstants.RIGHT);
        botao.setMargin(new Insets(0, 10, 0, 5));
        botao.setIconTextGap(10);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setFocusPainted(false);
        return botao;
     }

     private JPanel criarPainelSubMenu(JButton botao){
         JPanel painel = new JPanel();
         painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
         painel.setBackground(new Color(121212));
         painel.setVisible(false);
         painel.add(Box.createVerticalStrut(2));
         painel.add(botao);
         return painel;
     }

     //Getters
    public JButton getBtnCadastroCliente() {
        return btnCadastroCliente;
    }

    public JButton getBtnCadastroProduto() {
        return btnCadastroProduto;
    }

    public JButton getBtnEstoqueBarris() {
        return btnEstoqueBarris;
    }

    public JButton getBtnEstoqueChoppeiras() {
        return btnEstoqueChoppeiras;
    }

    public JButton getBtnEstoqueCilindros() {
        return btnEstoqueCilindros;
    }

    public JButton getBtnPedidoRegistrar() {
        return btnPedidoRegistrar;
    }

    public JButton getBtnPedidoConsultar() {
        return btnPedidoConsultar;
    }

    public JButton getBtnFinanceiroEntradas() {
        return btnFinanceiroEntradas;
    }

    public JButton getBtnFinanceiroResumo() {
        return btnFinanceiroResumo;
    }

    public JPanel getMenuLateral() {
        return menuLateral;
    }

    public JPanel getPainelSubmenuCadastro() {
        return painelSubmenuCadastro;
    }

    public JPanel getPainelSubmenuEstoque() {
        return painelSubmenuEstoque;
    }

    public JPanel getPainelSubmenuPedido() {
        return painelSubmenuPedido;
    }

    public JPanel getPainelSubmenuFinanceiro() {
        return painelSubmenuFinanceiro;
    }

    public boolean isMenuAberto() {
        return menuAberto;
    }

    public void setMenuAberto(boolean menuAberto) {
        this.menuAberto = menuAberto;
    }

    public JPanel getSubmenuAberto() {
        return submenuAberto;
    }

    public void setSubmenuAberto(JPanel submenuAberto) {
        this.submenuAberto = submenuAberto;
    }

    public JButton getBtnSair() {
        return btnSair;
    }


    public JButton getBtnAjuda() {
        return btnAjuda;
    }

    public JButton getBtnFinanceiro() {
        return btnFinanceiro;
    }

    public JButton getBtnEstoque() {
        return btnEstoque;
    }

    public JButton getBtnMenu() {
        return btnMenu;
    }


    public JButton getBtnPedido() {
        return btnPedido;
    }

    public JButton getBtnCadastro() {
        return btnCadastro;
    }

    public static void main(String[] args) {

    }
}
