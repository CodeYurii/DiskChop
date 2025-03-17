package com.diskchop.controller;

import com.diskchop.view.NovoPedido;
import com.github.lgooddatepicker.components.TimePicker;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class NovoPedidoController {
    private NovoPedido view;

    public NovoPedidoController(NovoPedido view) {
        this.view = view;
        configurarTela();
        configureActions();
    }

    public void configurarTela() {
        view.setLocationRelativeTo(null);
        view.getLabelNumeroTotalPedido().setText("0,00");
        telaInicial();
    }

    public void configureActions(){
        view.getBotaoVoltar().addActionListener(e -> {view.dispose();});
        view.getBotaoNovoPedido().addActionListener(e -> {adicionarPedidoBotao(); ligaCampos(); desligaCamposProduto();});
        view.getBotaoSalvarPedido().addActionListener(e -> {resetBotao(); desligaCampos(); desligaCamposProduto();});
        view.getBotaoCancelarNovoPedido().addActionListener(e -> {resetBotao(); desligaCampos(); desligaCamposProduto();});
        view.getBotaoAdicionarProduto().addActionListener(e -> {adicionarProdutoBotao(); ligaCamposProduto(); desligaCampos();});
        view.getBotaoSalvarProduto().addActionListener(e -> {desligarAdicionarProdutoBotao(); desligaCamposProduto(); ligaCampos();});
        view.getBotaoCancelarProduto().addActionListener(e -> {desligarAdicionarProdutoBotao(); desligaCamposProduto(); ligaCampos();});
    }

    public void telaInicial(){
        desligaCampos();
        resetBotao();
    }

    public void resetBotao(){
        view.getBotaoNovoPedido().setEnabled(true);
        view.getBotaoBuscarCliente().setEnabled(false);
        view.getBotaoCancelarNovoPedido().setEnabled(false);
        view.getBotaoLimpaCampos().setEnabled(false);
        view.getBotaoSalvarPedido().setEnabled(false);
        view.getBotaoImprimirPedido().setEnabled(false);
        view.getBotaoAdicionarProduto().setEnabled(false);
        view.getBotaoExcluirProduto().setEnabled(false);
        view.getBotaoSalvarProduto().setEnabled(false);
        view.getBotaoCancelarProduto().setEnabled(false);
    }

    public void adicionarPedidoBotao(){
        view.getBotaoNovoPedido().setEnabled(false);
        view.getBotaoBuscarCliente().setEnabled(true);
        view.getBotaoCancelarNovoPedido().setEnabled(true);
        view.getBotaoLimpaCampos().setEnabled(true);
        view.getBotaoSalvarPedido().setEnabled(true);
        view.getBotaoImprimirPedido().setEnabled(false);
        view.getBotaoAdicionarProduto().setEnabled(true);
        view.getBotaoExcluirProduto().setEnabled(true);
        view.getBotaoSalvarProduto().setEnabled(false);
        view.getBotaoCancelarProduto().setEnabled(false);
    }

    public void adicionarProdutoBotao(){
        view.getBotaoNovoPedido().setEnabled(false);
        view.getBotaoBuscarCliente().setEnabled(false);
        view.getBotaoCancelarNovoPedido().setEnabled(false);
        view.getBotaoLimpaCampos().setEnabled(false);
        view.getBotaoSalvarPedido().setEnabled(false);
        view.getBotaoImprimirPedido().setEnabled(false);
        view.getBotaoAdicionarProduto().setEnabled(false);
        view.getBotaoExcluirProduto().setEnabled(false);
        view.getBotaoSalvarProduto().setEnabled(true);
        view.getBotaoCancelarProduto().setEnabled(true);
    }

    public void desligarAdicionarProdutoBotao(){
        view.getBotaoNovoPedido().setEnabled(false);
        view.getBotaoBuscarCliente().setEnabled(true);
        view.getBotaoCancelarNovoPedido().setEnabled(true);
        view.getBotaoLimpaCampos().setEnabled(true);
        view.getBotaoSalvarPedido().setEnabled(true);
        view.getBotaoImprimirPedido().setEnabled(false);
        view.getBotaoAdicionarProduto().setEnabled(true);
        view.getBotaoExcluirProduto().setEnabled(true);
        view.getBotaoSalvarProduto().setEnabled(false);
        view.getBotaoCancelarProduto().setEnabled(false);
    }

    public void desligaCamposProduto(){
        view.getComboBoxProdutoCategoria().setEnabled(false);
        view.getComboBoxSelecionarProduto().setEnabled(false);
        view.getSpinQuantidade().setEnabled(false);
        view.getTextFieldProdutoPrecoUn().setEnabled(false);
        view.getTextFieldProdutoTotal().setEnabled(false);
        view.getCheckProdutoConsignado().setEnabled(false);
    }

    public void ligaCamposProduto(){
        view.getComboBoxProdutoCategoria().setEnabled(true);
        view.getComboBoxSelecionarProduto().setEnabled(true);
        view.getSpinQuantidade().setEnabled(true);
        view.getTextFieldProdutoPrecoUn().setEnabled(true);
        view.getTextFieldProdutoTotal().setEnabled(true);
        view.getCheckProdutoConsignado().setEnabled(true);
    }

    public void ligaCampos(){
       view.getTextFieldIdPedido().setEnabled(true);
       view.getTextFieldCliente().setEnabled(true);
       view.getTextFieldCpf().setEnabled(true);
       view.getTextFieldObs().setEnabled(true);
       view.getTextFieldDesconto().setEnabled(true);
       view.getTextFieldTaxaEntrega().setEnabled(true);
       view.getComboBoxEnderecoEntrega().setEnabled(true);
       view.getComboBoxEnderecoRetirada().setEnabled(true);
       view.getDateEntrega().setEnabled(true);
       view.getDateRetirada().setEnabled(true);
       view.getTimeEntrega().setEnabled(true);
       view.getTimeRetirada().setEnabled(true);
       view.getRadioSemTaxa().setEnabled(true);
       view.getCheckExtensao().setEnabled(true);
       view.getCheckPingador().setEnabled(true);
       view.getComboBoxFormaPagamento().setEnabled(true);
       view.getComboBoxStatusPagamento().setEnabled(true);
    }

    public void desligaCampos(){
        view.getTextFieldIdPedido().setEnabled(false);
        view.getTextFieldCliente().setEnabled(false);
        view.getTextFieldCpf().setEnabled(false);
        view.getTextFieldObs().setEnabled(false);
        view.getTextFieldDesconto().setEnabled(false);
        view.getTextFieldTaxaEntrega().setEnabled(false);
        view.getComboBoxEnderecoEntrega().setEnabled(false);
        view.getComboBoxEnderecoRetirada().setEnabled(false);
        view.getDateEntrega().setEnabled(false);
        view.getDateRetirada().setEnabled(false);
        view.getTimeEntrega().setEnabled(false);
        view.getTimeRetirada().setEnabled(false);
        view.getRadioSemTaxa().setEnabled(false);
        view.getCheckExtensao().setEnabled(false);
        view.getCheckPingador().setEnabled(false);
        view.getComboBoxFormaPagamento().setEnabled(false);
        view.getComboBoxStatusPagamento().setEnabled(false);
    }
}
