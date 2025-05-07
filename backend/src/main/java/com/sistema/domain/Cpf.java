package com.sistema.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public class Cpf {
    private String cpfString;

    @JsonCreator
    public Cpf(String cpfString) {
        setCpf(cpfString);
    }

    @JsonValue
    public String getValor() {
        return cpfString;
    }

    private void setCpf(String cpfString) {
        if (cpfString.length() != 11 || cpfString.matches("(\\d)\\1{10}")) {
            throw new IllegalArgumentException("CPF inválido");
        }
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(cpfString.charAt(i));
            sum += digit * (10 - i);
        }
        sum = (sum * 10) % 11;
        if (sum == 10) {
            sum = 0;
        }
        if (sum != Character.getNumericValue(cpfString.charAt(9))) {
            throw new IllegalArgumentException("o cpf nao passou no teste de verificacao");
        }
        int sum2 = 0;
        for (int i = 0; i < 10; i++) {
            int digit = Character.getNumericValue(cpfString.charAt(i));
            sum2 += digit * (11 - i);
        }
        sum2 = (sum2 * 10) % 11;
        if (sum2 != Character.getNumericValue(cpfString.charAt(10))) {
            throw new IllegalArgumentException("o cpf nao passou no teste de verificacao");
        }
        this.cpfString = cpfString;
    }
}
