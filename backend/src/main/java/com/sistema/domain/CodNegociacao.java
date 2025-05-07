package com.sistema.domain;

public class CodNegociacao {

    private String codString;

    public CodNegociacao(String codString) {
        setCodNegociacao(codString);
    }

    public String getValor() {
        return codString;
    }

    private void setCodNegociacao(String codString) {

        if (codString.length() < 0 || codString.length() > 13) {
            throw new IllegalArgumentException("O codigo de negociacao deve ter entre 0 e 13 caracteres");
        }
        for (char c : codString.toCharArray()) {
            if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) {
                throw new IllegalArgumentException(
                        "O codigo de negociacao deve conter apenas letras, digitos ou espacos em branco");
            }
        }
        this.codString = codString;
    }
}