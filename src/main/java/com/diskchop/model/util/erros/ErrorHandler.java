package com.diskchop.model.util.erros;

import com.diskchop.model.util.TelaMensagensSistema;

public class ErrorHandler {

    public static void handle(Exception e) {
        if (e instanceof ClienteInvalidoException) {
            // Lidar com erro específico de cliente inválido
            TelaMensagensSistema.mostrarErro(e.getMessage());
        } else {
            // Lidar com outros tipos de erro genérico
            TelaMensagensSistema.mostrarErro("Erro inesperado: " + e.getMessage());
        }
    }
}
