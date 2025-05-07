package com.sistema.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Senha {
    public String senhaString;

    @JsonCreator
    public Senha(String senhaString) {
        setSenha(senhaString);
    }

    @JsonValue
    public String getValor() {
        return senhaString;
    }

    private void setSenha(String senhaString) {
        if (senhaString == null || senhaString.length() != 6) {
            throw new IllegalArgumentException("A senha deve ter exatamente 6 caracteres.");
        }

        if (!senhaString.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[#\\$%&])[0-9A-Za-z#\\$%&]{6}$")) {
            throw new IllegalArgumentException("A senha não atende aos critérios de validação.");
        }

        for (int i = 0; i < senhaString.length(); i++) {
            char c = senhaString.charAt(i);
            if (senhaString.indexOf(c) != senhaString.lastIndexOf(c)) {
                throw new IllegalArgumentException("A senha não pode conter caracteres duplicados.");
            }
        }

        this.senhaString = senhaString;
    }
}
