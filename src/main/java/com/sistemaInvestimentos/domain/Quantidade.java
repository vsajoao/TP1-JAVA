package domain;

public class Quantidade {
    public int quantidade;

    public Quantidade(int quantidade) {
        setQuantidade(quantidade);
    }

    public int getValor() {
        return quantidade;
    }

    private void setQuantidade(int quantidade) {
        if (quantidade < 1 || quantidade > 1000000) {
            throw new IllegalArgumentException("Quantidade inválida. Deve ser um número entre 1 e 1.000.000.");
        }
        this.quantidade = quantidade;
    }
}
