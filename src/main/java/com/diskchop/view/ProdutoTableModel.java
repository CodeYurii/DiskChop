package com.diskchop.view;

import com.diskchop.model.entity.Barril;
import com.diskchop.model.entity.Produto;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProdutoTableModel extends AbstractTableModel {
    private final String[] colunas = {"ID", "Nome", "Quantidade", "Preço", "Categoria", "Validade"};
    private final List<Produto> produtos;

    // Construtor sem parâmetros, inicializa lista vazia
    public ProdutoTableModel() {
        this.produtos = new ArrayList<>();
    }

    // Construtor com lista de produtos
    public ProdutoTableModel(List<Produto> produtos) {
        this.produtos = (produtos != null) ? produtos : new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return produtos.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = produtos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> produto.getId();
            case 1 -> produto.getNome();
            case 2 -> produto.getQuantidade();
            case 3 -> produto.getPreco();
            case 4 -> produto.getCategoria();
            case 5 -> (produto instanceof Barril) ? ((Barril) produto).getValidade() : "-"; // Exibe validade só para barris
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colunas[column];
    }

    public Produto getProdutoAt(int rowIndex) {
        return produtos.get(rowIndex);
    }

    public void adicionarLista(List<Produto> produtos) {
        this.produtos.addAll(produtos);
        fireTableDataChanged();
    }


    // Método para adicionar um produto e atualizar a tabela
    public void addProduto(Produto produto) {
        produtos.add(produto);
        fireTableRowsInserted(produtos.size() - 1, produtos.size() - 1);
    }

    // Método para limpar a tabela
    public void limparTabela() {
        produtos.clear();
        fireTableDataChanged();
    }
}
