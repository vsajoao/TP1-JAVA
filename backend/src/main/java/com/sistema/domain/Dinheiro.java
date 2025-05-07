package com.sistema.domain;


public class Dinheiro {
    private final double valor;

    public Dinheiro(double valor) {
        validarValor(valor);
        this.valor = valor;
    }

    private void validarValor(double valor) {
        if (valor < 0.01 || valor > 1_000_000.00) {
            throw new IllegalArgumentException(
                "Valor monetário inválido. Deve ser entre 0,01 e 1.000.000,00"
            );
        }
    }

    public double getValor() {
        return valor;
    }


    @Override
    public String toString() {
    return String.valueOf(valor);
}
}
