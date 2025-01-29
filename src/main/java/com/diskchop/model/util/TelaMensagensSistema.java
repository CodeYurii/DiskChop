package com.diskchop.model.util;

import javax.swing.*;

public class TelaMensagensSistema {

    public static void mostrarInformacao(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public static void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static boolean mostrarConfirmacao(String mensagem) {
        int opcao = JOptionPane.showConfirmDialog(null, mensagem, "Confirmação",
                JOptionPane.YES_NO_OPTION);
        return opcao == JOptionPane.YES_OPTION;
    }
}