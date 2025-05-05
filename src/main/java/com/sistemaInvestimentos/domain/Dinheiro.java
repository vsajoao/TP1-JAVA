package domain;

import java.text.NumberFormat;
import java.util.Locale;

public class Dinheiro {
    private final double valor;

    public Dinheiro(double valor) {
        validarValor(valor);
        this.valor = valor;
    }

    private void validarValor(double valor) {
        if (valor < 0.01 || valor > 1_000_000.00) {
            throw new IllegalArgumentException(
                    "Valor monetário inválido. Deve ser entre 0,01 e 1.000.000,00");
        }
    }

    public double getValor() {
        return valor;
    }

    public String getValorFormatado() {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR"))
                .format(valor)
                .replace("R$", "")
                .trim();
    }

    @Override
    public String toString() {
        return String.format("R$ %.2f", valor);
    }
}