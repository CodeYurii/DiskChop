package com.diskchop.controller;

import javax.swing.ImageIcon;
import java.awt.Image;

public class CarregadorIcones {

    // Método para carregar e redimensionar o ícone
    public static ImageIcon loadIcon(String path, int width, int height) {
        // Carregar o ícone original
        ImageIcon icon = new ImageIcon(CarregadorIcones.class.getResource(path));

        // Redimensionar o ícone
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Retornar o ícone redimensionado
        return new ImageIcon(image);
    }

    public static ImageIcon loadSubmenuIcon(String path, int width, int height) {
        // Carregar o ícone original
        ImageIcon icon = new ImageIcon(CarregadorIcones.class.getResource(path));

        // Redimensionar o ícone
        Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Retornar o ícone redimensionado
        return new ImageIcon(image);
    }
}
