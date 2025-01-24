package com.diskchop;

import com.diskchop.controller.MainController;
import com.diskchop.view.TelaInicialMenu;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Cria a tela
            TelaInicialMenu view = new TelaInicialMenu();

            // Cria o controlador e passa a view para ele
            MainController controller = new MainController(view);

            // Configura os listeners
            controller.configureActions();

            // Exibe a tela
            view.setVisible(true);
        });
    }
}