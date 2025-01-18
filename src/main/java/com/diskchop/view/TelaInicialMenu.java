package com.diskchop.view;
import com.diskchop.controller.CarregadorIcones;
import com.diskchop.controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaInicialMenu extends JFrame {
    private MainController controller;
    //Variáveis globais
    private JPanel menuLateral; // Painel do menu lateral
    private boolean menuAberto = false; // Estado do menu (aberto ou fechado)
    private JPanel painelSubmenuCadastro, painelSubmenuEstoque, painelSubmenuPedido, painelSubmenuFinanceiro;
    JPanel submenuAberto = null;
    JButton btnCadastro;
    JButton btnPedido;
    JButton btnMenu;
    JButton btnEstoque;
    JButton btnFinanceiro;
    JButton btnAjuda;
    JButton btnSair;

    /*public TelaInicialMenu(MainController controller) {
        this.controller = controller;
        inicializarComponentes();
    }*/
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
        setVisible(true); // Torna a janela visível




        // Botão do menu
        btnMenu = new JButton(CarregadorIcones.loadIcon("/icones/menu2.png",32,32));
        btnMenu.setMargin(new Insets(5, 5, 5, 5));
        btnMenu.setFocusPainted(false);
        btnMenu.setBorderPainted(false);
        btnMenu.setBackground(new Color(248, 248, 248));
        btnMenu.setForeground(Color.WHITE);
        //btnMenu.addActionListener(e -> controller.toggleMenu());


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
        btnCadastro = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/cadastro2.png",32, 32), painelSubmenuPedido);
        btnCadastro.setText("Cadastro");

        btnPedido = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/pedido2.png",32, 32), painelSubmenuPedido);
        btnPedido.setText("Pedido");
        btnPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlarSubmenu(painelSubmenuPedido);
            }
        });

        btnEstoque = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/estoqueBarril2.png",32,32), painelSubmenuEstoque);
        btnEstoque.setText("Estoque");
        btnEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlarSubmenu(painelSubmenuEstoque);
            }
        });

        btnFinanceiro = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/financeiro2.png",32,32), painelSubmenuFinanceiro);
        btnFinanceiro.setText("Financeiro");
        btnFinanceiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlarSubmenu(painelSubmenuFinanceiro);
            }
        });

        btnAjuda = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/ajuda.png",32,32), null);
        btnAjuda.setText("Ajuda");
        btnAjuda.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fechar todos os submenus abertos
                if (submenuAberto != null) {
                    submenuAberto.setVisible(false); // Fecha o submenu aberto
                    submenuAberto = null; // Nenhum submenu está aberto
                }
            }
        });

        btnSair = criarBotaoMenu(CarregadorIcones.loadIcon("/icones/sair2.png",32,32), null);
        btnSair.setText("Sair");
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

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

        double MinhaContaBancaria = 0;

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
        botao.setForeground(new Color(34, 139, 34));
        botao.setMaximumSize(new Dimension(180, 60));
        botao.setHorizontalAlignment(SwingConstants.LEFT);
        botao.setHorizontalTextPosition(SwingConstants.RIGHT);
        botao.setMargin(new Insets(0, 10, 0, 5));
        botao.setIconTextGap(10);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        return botao;
    }


    private JPanel criarSubmenu(String titulo, String[] opcoes, ImageIcon[] icones) {
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBackground(new Color(173, 216, 170));
        painel.setVisible(false);

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


    public void controlarSubmenu(JPanel submenu) {
        if (submenuAberto != submenu) {
            if (submenuAberto != null) {
                submenuAberto.setVisible(false); // Fecha o submenu aberto
            }
            submenu.setVisible(true); // Abre o submenu clicado
            submenuAberto = submenu; // Atualiza o submenu aberto
        } else {
            submenu.setVisible(false); // Fecha o submenu se ele já estiver aberto
            submenuAberto = null; // Nenhum submenu está aberto
        }
    }


    public JPanel getMenuLateral() {
        return menuLateral;
    }

    public void setMenuLateral(JPanel menuLateral) {
        this.menuLateral = menuLateral;
    }

    public JPanel getPainelSubmenuCadastro() {
        return painelSubmenuCadastro;
    }

    public void setPainelSubmenuCadastro(JPanel painelSubmenuCadastro) {
        this.painelSubmenuCadastro = painelSubmenuCadastro;
    }

    public JPanel getPainelSubmenuEstoque() {
        return painelSubmenuEstoque;
    }

    public void setPainelSubmenuEstoque(JPanel painelSubmenuEstoque) {
        this.painelSubmenuEstoque = painelSubmenuEstoque;
    }

    public JPanel getPainelSubmenuPedido() {
        return painelSubmenuPedido;
    }

    public void setPainelSubmenuPedido(JPanel painelSubmenuPedido) {
        this.painelSubmenuPedido = painelSubmenuPedido;
    }

    public JPanel getPainelSubmenuFinanceiro() {
        return painelSubmenuFinanceiro;
    }

    public void setPainelSubmenuFinanceiro(JPanel painelSubmenuFinanceiro) {
        this.painelSubmenuFinanceiro = painelSubmenuFinanceiro;
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

    public void setBtnSair(JButton btnSair) {
        this.btnSair = btnSair;
    }

    public JButton getBtnAjuda() {
        return btnAjuda;
    }

    public void setBtnAjuda(JButton btnAjuda) {
        this.btnAjuda = btnAjuda;
    }

    public JButton getBtnFinanceiro() {
        return btnFinanceiro;
    }

    public void setBtnFinanceiro(JButton btnFinanceiro) {
        this.btnFinanceiro = btnFinanceiro;
    }

    public JButton getBtnEstoque() {
        return btnEstoque;
    }

    public void setBtnEstoque(JButton btnEstoque) {
        this.btnEstoque = btnEstoque;
    }

    public JButton getBtnMenu() {
        return btnMenu;
    }

    public void setBtnMenu(JButton btnMenu) {
        this.btnMenu = btnMenu;
    }

    public JButton getBtnPedido() {
        return btnPedido;
    }

    public void setBtnPedido(JButton btnPedido) {
        this.btnPedido = btnPedido;
    }

    public JButton getBtnCadastro() {
        return btnCadastro;
    }

    public void setBtnCadastro(JButton btnCadastro) {
        this.btnCadastro = btnCadastro;
    }

    public static void main(String[] args) {

    }
}
