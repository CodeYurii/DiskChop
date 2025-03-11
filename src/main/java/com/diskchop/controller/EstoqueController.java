package com.diskchop.controller;

import com.diskchop.model.dao.ProdutoDao;
import com.diskchop.model.entity.*;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import com.diskchop.view.Estoque;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EstoqueController {
    private Estoque view;
    private ProdutoDao produtoDao;

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
        modeloTabela();
    }

    public void configureActions(){
        view.getBotaoNovoProduto().addActionListener(e ->{ligaDesligaBotoesProduto();});
        view.getBotaoCancelarProduto().addActionListener(e ->{ligaDesligaBotoesProduto();});
        view.getBotaoSalvar().addActionListener(e ->{ligaDesligaBotoesProduto(); salvarProduto(); buscarCategoriaMaquina();});
        view.getBotaoBuscar().addActionListener(e ->{buscarCategoriaMaquina();});
    }

    /***  CRUD BOTOES ***/
    private void salvarProduto(){
        Produto produto = carregarInformacoesProduto();
        try{
            produtoDao.salvarProduto(produto);
            TelaMensagensSistema.mostrarInformacao("Salvo com sucesso!");
        } catch (Exception e){
            e.printStackTrace();
            TelaMensagensSistema.mostrarErro("Erro ao salvar produto");
        }
    }

    /*** BUSCA  ***/
    private void buscarCategoriaMaquina() {
        try{
            List<Maquina> maquinas;
            maquinas = produtoDao.buscarMaquinas();
            DefaultTableModel modeloMaquina = modeloTabelaMaquina();
            escreverTabelaMaquina(maquinas, modeloMaquina);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /***  TABELA ***/
    private void modeloTabela(){
        view.getTabela().setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "PRODUTO", "CATEGORIA", "QUANTIDADE", "PREÇO", "STATUS", "VALIDADE"} // Cabeçalhos das colunas
        ));
    }
    private DefaultTableModel modeloTabelaMaquina(){
        DefaultTableModel modeloMaquina = new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "PRODUTO", "QUANTIDADE", "PREÇO", "STATUS"}
        );
        view.getTabelaProdutos().setModel(modeloMaquina);
        modeloMaquina.setRowCount(0);
        return modeloMaquina;
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
    /*** CARREGAMENTO INFORMAÇÕES DA TELA ***/
    private Barril carregaInformacaoBarril(){
        Barril barril = new Barril();
        barril.setNome(view.getTextFieldProduto().getText());
        barril.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        barril.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        barril.setValidade(view.getDateChooserValidade().getDate());
        return barril;
    }

    private Maquina carregaInformacaoMaquina(){
        Maquina maquina = new Maquina();
        maquina.setNome(view.getTextFieldProduto().getText());
        maquina.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        maquina.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        return maquina;
    }

    private Cilindro carregaInformacaoCilindro(){
        Cilindro cilindro = new Cilindro();
        cilindro.setNome(view.getTextFieldProduto().getText());
        cilindro.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        cilindro.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        return cilindro;
    }

    private Mesa carregaInformacaoMesa(){
        Mesa mesa = new Mesa();
        mesa.setNome(view.getTextFieldProduto().getText());
        mesa.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        mesa.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
        return mesa;
    }

    private Diverso carregaInformacaoDiverso(){
        Diverso diverso = new Diverso();
        diverso.setNome(view.getTextFieldProduto().getText());
        diverso.setQuantidade(Integer.valueOf(view.getTextFieldQuantidade().getText()));
        diverso.setPreco(Double.valueOf(view.getTextFieldPreco().getText()));
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
