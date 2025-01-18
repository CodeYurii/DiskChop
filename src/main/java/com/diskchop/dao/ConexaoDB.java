package com.diskchop.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    private static final String URL = "jdbc:sqlite:src/main/resources/meubanco.db";

    public static Connection connect() {
        Connection connection = null;
        try {
            // Cria uma conexão com o banco de dados
            connection = DriverManager.getConnection(URL);
            System.out.println("Conexão estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco: " + e.getMessage());
        }
        return connection;
    }
}

