package com.diskchop.controller;

import com.diskchop.model.dao.ProdutoDao;
import com.diskchop.model.entity.*;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import com.diskchop.view.Estoque;
import com.diskchop.view.ProdutoTableModel;
import jakarta.persistence.NoResultException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EstoqueController {
    private Estoque view;
    private ProdutoDao produtoDao;
    private Produto produtoSelecionado;


    public EstoqueController(Estoque view) {
        this.view = view;
        this.produtoDao = new ProdutoDao();
        configurarTela();
        configureActions();
    }

    /***  CONFIGURAÇÕES DA TELA E BOTOES  ***/
    public void configurarTela() {
        view.setLocationRelativeTo(null);
        view.getBotaoCancelarProduto().setEnabled(false);
        view.getBotaoSalvar().setEnabled(false);
        //modeloTabela();
        tabelaInicial();

    }


    public void configureActions(){
        view.getBotaoVoltarTela().addActionListener(e -> {view.dispose();});
        view.getBotaoNovoProduto().addActionListener(e ->{ligaDesligaBotoesProduto(); limparTabela(view.getTabelaProdutos()); resetarProduto();});
        view.getBotaoEditarProduto().addActionListener(e ->{ligaDesligaBotoesProduto(); });
        view.getBotaoExcluirProduto().addActionListener(e ->{limparTabela(view.getTabelaProdutos()); excluirProduto(); buscarCategoria();});
        view.getBotaoCancelarProduto().addActionListener(e ->{ligaDesligaBotoesProduto();});
        view.getBotaoSalvar().addActionListener(e ->{ligaDesligaBotoesProduto(); salvarProduto(); buscarCategoriaMaquina();});
        view.getBotaoBuscar().addActionListener(e ->{buscarCategoria();});
        view.getTabelaProdutos().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linhaSelecionada = view.getTabelaProdutos().getSelectedRow();
                if (linhaSelecionada != -1) {
                    carregarDadosParaCampos(linhaSelecionada);
                }
            }
        });

    }

    private Produto carregarDadosParaCampos(int linhaSelecionada) {
        ProdutoTableModel modelo = (ProdutoTableModel) view.getTabelaProdutos().getModel();
        produtoSelecionado = modelo.getProdutoAt(linhaSelecionada);

        view.getTextFieldProduto().setText(produtoSelecionado.getNome());
        view.getTextFieldQuantidade().setText(String.valueOf(produtoSelecionado.getQuantidade()));
        view.getTextFieldPreco().setText(String.valueOf(produtoSelecionado.getPreco()));
        view.getComboProdutoCategoria().setSelectedItem(produtoSelecionado.getCategoria().toString());
        System.out.println(produtoSelecionado.getCategoria());
        view.getComboStatus().setSelectedItem(produtoSelecionado.getStatusProduto());
        return produtoSelecionado;
    }

    private void resetarProduto(){
        this.produtoSelecionado = null;
    }

    /***  CRUD BOTOES ***/
    private void salvarProduto(){
        try{
            if (produtoSelecionado == null) {
                Produto produto = carregarInformacoesProduto();
                produtoDao.salvarProduto(produto);
                TelaMensagensSistema.mostrarInformacao("produto salvo");
            } else {
                Produto produto = produtoDao.buscarId(produtoSelecionado.getId());
                if (produto != null) {
                    Produto produtoAtualizado = carregarInformacoesProduto();
                    produtoAtualizado.setId(produto.getId());
                    produtoDao.atualizarProduto(produtoAtualizado);
                } else {
                    TelaMensagensSistema.mostrarErro("Produto não encontrado para atualização.");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            TelaMensagensSistema.mostrarErro("Erro ao salvar produto");
        }
    }

    public void excluirProduto(){
        try{
            produtoDao.excluirProduto(produtoSelecionado);
            TelaMensagensSistema.mostrarInformacao("produto excluido");
        }catch (Exception e){
            e.printStackTrace();
            TelaMensagensSistema.mostrarErro("Erro ao excluir produto");
        }
    }

    /*** BUSCA  ***/
    private void buscarCategoria(){
        try{
            String categoriaSelecionada = view.getComboCategoriaBusca(); // Pegando a categoria da View
            CategoriaProduto categoria = CategoriaProduto.valueOf(categoriaSelecionada.toUpperCase());
            List<Produto> produtos = produtoDao.buscarProdutoCategoria(categoria);
            ProdutoTableModel modelo = new ProdutoTableModel(produtos);
            view.getTabelaProdutos().setModel(modelo);
            //DefaultTableModel modeloMaquina = modeloTabelaMaquina();
            //escreverTabelaProduto(produtos, modeloMaquina);
        } catch (NoResultException e) {
            throw new RuntimeException("nenhum produto encontrado", e);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar produtos pela categoria", e);
        }
    }
    private void buscarCategoriaMaquina() {
        try{
            List<Maquina> maquinas;
            maquinas = produtoDao.buscarMaquinas();
            ProdutoTableModel modeloMaquina = modeloTabelaMaquina();
            //escreverTabelaMaquina(maquinas, modeloMaquina);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /***  TABELA ***/
    private void tabelaInicial(){
        List<Produto> listaProdutos = produtoDao.buscarTodosProdutos(); // Busca do banco de dados
        ProdutoTableModel modelo = new ProdutoTableModel(listaProdutos);
        view.getTabelaProdutos().setModel(modelo);
    }

    private void modeloTabela(){
        view.getTabelaProdutos().setModel(new ProdutoTableModel());

        /*
        view.getTabela().setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "PRODUTO", "CATEGORIA", "QUANTIDADE", "PREÇO", "STATUS", "VALIDADE"} // Cabeçalhos das colunas
        ));*/
    }
    private ProdutoTableModel modeloTabelaMaquina(){
        ProdutoTableModel modeloMaquina = new ProdutoTableModel();
        view.getTabelaProdutos().setModel(modeloMaquina);
        /*
        DefaultTableModel modeloMaquina = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "PRODUTO", "QUANTIDADE", "PREÇO", "STATUS"}
        );
        view.getTabelaProdutos().setModel(modeloMaquina);
        modeloMaquina.setRowCount(0);*/
        return modeloMaquina;
    }

    public void limparTabela(JTable tabela) {
        if (tabela.getModel() instanceof ProdutoTableModel) {
            ProdutoTableModel modelo = (ProdutoTableModel) tabela.getModel();
            modelo.limparTabela(); // Assumindo que você criou um método para limpar a lista de produtos
        }
        /*
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0); // Remove todas as linhas da tabela*/
    }


    private void escreverTabelaMaquina(List<Maquina>maquinas, DefaultTableModel modeloMaquina){
        for (Maquina maquina : maquinas) {
            Object[] rowData = {
                    maquina.getId(),
                    maquina.getNome(),
                    maquina.getQuantidade(),
                    maquina.getPreco(),
                    maquina.getStatusProduto()
            };
            modeloMaquina.addRow(rowData);
        }
    }

    private void escreverTabelaProduto(List<Produto>maquinas, ProdutoTableModel modeloMaquina){
        modeloMaquina.adicionarLista(maquinas);

        /*
        for (Produto maquina : maquinas) {
            Object[] rowData = {
                    maquina.getId(),
                    maquina.getNome(),
                    maquina.getQuantidade(),
                    maquina.getPreco(),
                    maquina.getStatusProduto()
            };
            modeloMaquina.addRow(rowData);
        }*/
    }
    /*** CARREGAMENTO INFORMAÇÕES DA TELA ***/
    private Barril carregaInformacaoBarril(){
        Barril barril = new Barril();
        barril.setNome(view.getTextFieldProduto().getText());
        barril.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        barril.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        barril.setStatusProduto(StatusProduto.valueOf(view.getComboStatusProduto()));
        barril.setCategoria(CategoriaProduto.valueOf(view.getComboCategoriaProduto()));
        barril.setValidade(view.getDateChooserValidade().getDate());
        return barril;
    }

    private Maquina carregaInformacaoMaquina(){
        Maquina maquina = new Maquina();
        maquina.setNome(view.getTextFieldProduto().getText());
        maquina.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        maquina.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        maquina.setCategoria(CategoriaProduto.valueOf(view.getComboCategoriaProduto()));
        maquina.setStatusProduto(StatusProduto.valueOf(view.getComboStatusProduto()));
        return maquina;
    }

    private Cilindro carregaInformacaoCilindro(){
        Cilindro cilindro = new Cilindro();
        cilindro.setNome(view.getTextFieldProduto().getText());
        cilindro.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        cilindro.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        cilindro.setStatusProduto(StatusProduto.valueOf(view.getComboStatusProduto()));
        cilindro.setCategoria(CategoriaProduto.valueOf(view.getComboCategoriaProduto()));
        return cilindro;
    }

    private Mesa carregaInformacaoMesa(){
        Mesa mesa = new Mesa();
        mesa.setNome(view.getTextFieldProduto().getText());
        mesa.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        mesa.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        mesa.setStatusProduto(StatusProduto.valueOf(view.getComboStatusProduto()));
        mesa.setCategoria(CategoriaProduto.valueOf(view.getComboCategoriaProduto()));
        return mesa;
    }

    private Diverso carregaInformacaoDiverso(){
        Diverso diverso = new Diverso();
        diverso.setNome(view.getTextFieldProduto().getText());
        diverso.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        diverso.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        diverso.setStatusProduto(StatusProduto.valueOf(view.getComboStatusProduto()));
        diverso.setCategoria(CategoriaProduto.valueOf(view.getComboCategoriaProduto()));
        return diverso;
    }


    private Produto carregarInformacoesProduto(){
        String categoria = view.getComboProdutoCategoria().getSelectedItem().toString();
        if(categoria.equals("BARRIL")){
            return carregaInformacaoBarril();
        } else if(categoria.equals("MAQUINA")) {
            return carregaInformacaoMaquina();
        } else if(categoria.equals("CILINDRO")) {
            return carregaInformacaoCilindro();
        } else if(categoria.equals("MESA")) {
            return carregaInformacaoMesa();
        } else {
            return carregaInformacaoDiverso();
        }
    }

    /***  FUNCIONALIDADES BOTOES  ***/
    private void ligarBotoesSalvarProduto(){
        for (JButton botao : view.getBotoesSalvar()) {
            if (botao.isEnabled()) {
                botao.setEnabled(false);
            } else {
                botao.setEnabled(true);
            }
        }
    }

    private void ligarBotoes(){
        for (JButton botao : view.getBotoes()) {
            if (botao.isEnabled()) {
                botao.setEnabled(false);
            } else {
                botao.setEnabled(true);
            }
        }
    }
    private void ligaDesligaBotoesProduto(){
        ligarBotoesSalvarProduto();
        ligarBotoes();
    }

}
