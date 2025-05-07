package com.sistema.domain;

public class Nome {

    public String nomeString;

    public Nome(String nomeString) {
        setNome(nomeString);
    }

    public String getValor() {
        return nomeString;
    }

    private void setNome(String nomeString) {
        if (nomeString.length() > 20) {
            throw new IllegalArgumentException("O nome deve ter até 20 caracteres");
        }
        if (!nomeString.matches("[a-zA-Z0-9 ]*") || nomeString.contains("  ")) {
            throw new IllegalArgumentException(
                    "O nome deve conter apenas letras, dígitos ou espaços, e não pode ter dois espaços consecutivos");
        }
        this.nomeString = nomeString;
    }
}
