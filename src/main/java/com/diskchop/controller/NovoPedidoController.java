package com.diskchop.controller;

import com.diskchop.view.NovoPedido;

public class NovoPedidoController {
    private NovoPedido view;

    public NovoPedidoController(NovoPedido view) {
        this.view = view;
        configurarTela();
    }

    public void configurarTela() {
        view.setLocationRelativeTo(null);
    }
}
