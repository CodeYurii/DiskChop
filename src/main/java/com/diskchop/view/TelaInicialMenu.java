package com.diskchop.view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TelaInicialMenu extends JFrame {

    //Variáveis globais
    private JPanel menuLateral; // Painel do menu lateral
    private boolean menuAberto = false; // Estado do menu (aberto ou fechado)
    private JPanel painelSubmenuCadastro, painelSubmenuEstoque, painelSubmenuPedido, painelSubmenuFinanceiro;
    private List<JPanel> submenusAbertos = new ArrayList<>();


    public TelaInicialMenu() {
        // Configurações da janela principal
        setTitle("Software");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Janela em tela cheia
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        // Botão do menu
        ImageIcon iconMenu = new ImageIcon(getClass().getResource("/icones/menu2.png"));
        Image imageMenu = iconMenu.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionadoMenu = new ImageIcon(imageMenu);
        JButton btnMenu = new JButton(iconRedimensionadoMenu);
        btnMenu.setMargin(new Insets(5, 5, 5, 5));
        btnMenu.setFocusPainted(false);
        btnMenu.setBorderPainted(false);
        btnMenu.setBackground(new Color(248, 248, 248));
        btnMenu.setForeground(Color.WHITE);
        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMenu(); // Alterna o estado do menu
            }
        });

        // Cabeçalho Superior
        JPanel cabecalho = new JPanel();
        cabecalho.setBackground(new Color(248, 248, 248)); // Verde escuro agradável
        cabecalho.setPreferredSize(new Dimension(getWidth(), 60));
        cabecalho.setLayout(new BorderLayout());
        JLabel titulo = new JLabel("DISK CHOPP SUZANO", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 40));
        titulo.setForeground(new Color(0, 77, 64));
        cabecalho.add(btnMenu, BorderLayout.WEST); // Ícone do menu à esquerda
        cabecalho.add(titulo, BorderLayout.CENTER); // Título ao centro


        // Menu Lateral
        menuLateral = new JPanel();
        menuLateral.setBackground(new Color(248, 248, 248)); // Cinza claro
        menuLateral.setPreferredSize(new Dimension(180, getHeight()));
        menuLateral.setLayout(new BoxLayout(menuLateral, BoxLayout.Y_AXIS)); // Itens em coluna
        menuLateral.setVisible(false);

        //Botões Menu
        ImageIcon iconCadastro = new ImageIcon(getClass().getResource("/icones/cadastro2.png"));
        Image imageCadastro = iconCadastro.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionadoCadastro = new ImageIcon(imageCadastro);
        JButton btnCadastro = criarBotaoMenu(iconRedimensionadoCadastro, painelSubmenuCadastro);
        btnCadastro.setText("Cadastro");

        ImageIcon iconPedido = new ImageIcon(getClass().getResource("/icones/pedido2.png"));
        Image imagePedido = iconPedido.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionadoPedido = new ImageIcon(imagePedido);
        JButton btnPedido = criarBotaoMenu(iconRedimensionadoPedido, painelSubmenuPedido);
        btnPedido.setText("Pedidos");

        ImageIcon iconEstoque = new ImageIcon(getClass().getResource("/icones/estoqueBarril2.png"));
        Image imageEstoque = iconEstoque.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionadoEstoque = new ImageIcon(imageEstoque);
        JButton btnEstoque = criarBotaoMenu(iconRedimensionadoEstoque, painelSubmenuEstoque);
        btnEstoque.setText("Estoque");

        ImageIcon iconFinanceiro = new ImageIcon(getClass().getResource("/icones/financeiro2.png"));
        Image imageFinanceiro = iconFinanceiro.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionadoFinanceiro = new ImageIcon(imageFinanceiro);
        JButton btnFinanceiro = criarBotaoMenu(iconRedimensionadoFinanceiro, painelSubmenuFinanceiro);
        btnFinanceiro.setText("Financeiro");

        ImageIcon iconAjuda = new ImageIcon(getClass().getResource("/icones/ajuda.png"));
        Image imageAjuda = iconAjuda.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionadoAjuda = new ImageIcon(imageAjuda);
        JButton btnAjuda = criarBotaoMenu(iconRedimensionadoAjuda, null);
        btnAjuda.setText("Ajuda");

        ImageIcon iconSair = new ImageIcon(getClass().getResource("/icones/sair2.png"));
        Image imageSair = iconSair.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon iconRedimensionadoSair = new ImageIcon(imageSair);
        JButton btnSair = criarBotaoMenu(iconRedimensionadoSair, null);
        btnSair.setText("Sair");

        // Criar submenus
        ImageIcon[] iconesCadastro = {
                new ImageIcon(getClass().getResource("/icones/cadastroUsuario.png")),
                new ImageIcon(getClass().getResource("/icones/cadastroProduto.png"))
        };
        String[] opcoesCadastro = {"Cadastrar Cliente", "Cadastrar Produto"};
        painelSubmenuCadastro = criarSubmenu("Cadastro", opcoesCadastro, iconesCadastro);

        ImageIcon[] iconesEstoque = {
                new ImageIcon(getClass().getResource("/icones/estoqueBarril.png")),
                new ImageIcon(getClass().getResource("/icones/choppeira.png")),
                new ImageIcon(getClass().getResource("/icones/estoqueCilindro.png"))
        };
        String[] opcoesEstoque = {"Estoque Barris", "Estoque Choppeiras", "Estoque Cilindros"};
        painelSubmenuEstoque = criarSubmenu("Estoque", opcoesEstoque, iconesEstoque);

        ImageIcon[] iconesPedido = {
                new ImageIcon(getClass().getResource("/icones/registrarPedido.png")),
                new ImageIcon(getClass().getResource("/icones/consultarPedido.png"))
        };
        String[] opcoesPedido = {"Registrar Pedido", "Consultar Pedido"};
        painelSubmenuPedido = criarSubmenu("Pedido", opcoesPedido, iconesPedido);

        ImageIcon[] iconesFinanceiro = {
                new ImageIcon(getClass().getResource("/icones/entradaSaida2.png")),
                new ImageIcon(getClass().getResource("/icones/relatorio.png"))
        };
        String[] opcoesFinanceiro = {"Entrada e Saídas", "Resumo"};
        painelSubmenuFinanceiro = criarSubmenu("Financeiro", opcoesFinanceiro, iconesFinanceiro);

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
        painelPrincipal.setBackground(new Color(0, 77, 64));

        // Adiciona os painéis ao JFrame
        add(cabecalho, BorderLayout.NORTH); // Cabeçalho superior
        add(menuLateral, BorderLayout.WEST); // Menu lateral
        add(painelPrincipal, BorderLayout.CENTER); // Área principal
        setVisible(true);
    }

    // Função para criar botões
    private JButton criarBotaoMenu(ImageIcon icone, JPanel submenu) {
        JButton botao = new JButton(icone);
        botao.setBackground(new Color(250,250,250));
        botao.setFont(new Font("Roboto", Font.BOLD, 16));
        botao.setFocusPainted(false);
        botao.setForeground(new Color(34, 139, 34)); // Verde no texto
        botao.setMaximumSize(new Dimension(180, 60));
        botao.setHorizontalAlignment(SwingConstants.LEFT); // Conteúdo do botão à esquerda
        botao.setHorizontalTextPosition(SwingConstants.RIGHT); // Texto à direita do ícone
        botao.setMargin(new Insets(0, 10, 0, 5));
        botao.setIconTextGap(10);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Exibe/oculta o submenu quando o botão é clicado
        if (submenu != null) {
            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    submenu.setVisible(!submenu.isVisible());
                    menuLateral.revalidate(); // Atualiza o layout do painel lateral
                    menuLateral.repaint(); // Alterna a visibilidade do submenu
                }
            });
        }
        return botao;
    }


    private JPanel criarSubmenu(String titulo, String[] opcoes, ImageIcon[] icones) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(173, 216, 170));
        painel.setVisible(false); // Submenu inicialmente oculto

        for (int i = 0; i < opcoes.length; i++) {
            JButton item = new JButton(opcoes[i]);

            // Definir ícone para o botão, se houver
            if (icones != null && icones.length > i) {
                item.setIcon(icones[i]);
                item.setHorizontalTextPosition(SwingConstants.RIGHT); // Ícone à esquerda do texto
                item.setIconTextGap(20); // Distância entre ícone e texto
            }
            item.setBackground(Color.WHITE);
            item.setFont(new Font("Roboto", Font.ITALIC, 12));
            item.setForeground(new Color(34, 139, 34));
            item.setMaximumSize(new Dimension(180, 40));
            item.setHorizontalAlignment(SwingConstants.LEFT);
            item.setHorizontalTextPosition(SwingConstants.RIGHT);
            item.setMargin(new Insets(0, 10, 0, 5));
            item.setIconTextGap(10);
            item.setAlignmentX(Component.CENTER_ALIGNMENT);
            item.setFocusPainted(false);
            painel.add(Box.createVerticalStrut(2));
            painel.add(item);
        }
        return painel;
    }

    // Alterna o estado do menu lateral
    private void toggleMenu() {
        menuAberto = !menuAberto;
        menuLateral.setVisible(menuAberto); // Mostra ou oculta o menu
    }

    public static void main(String[] args) {
        new TelaInicialMenu();
    }
}
