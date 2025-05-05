package entity;

import java.util.ArrayList;
import java.util.List;

import domain.Codigo;
import domain.Cpf;
import domain.Nome;
import domain.Senha;

public class Conta {
    private final Cpf cpf;
    private Nome nome;
    private Senha senha;
    private final List<Carteira> carteiras = new ArrayList<>();

    public List<Carteira> getCarteiras() {
        return new ArrayList<>(carteiras);
    }

    public Carteira obterCarteira(Codigo codigo) {
        return carteiras.stream()
                .filter(c -> c.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }

    public void adicionarCarteira(Carteira carteira) {
        carteiras.add(carteira);
    }

    public void removerCarteira(Carteira carteira) {
        carteiras.remove(carteira);
    }

    public double getSaldoTotal() {
        return carteiras.stream()
                .mapToDouble(Carteira::getSaldo)
                .sum();
    }

    public Conta(Cpf cpf, Nome nome, Senha senha) {
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Nome getNome() {
        return nome;
    }

    public Senha getSenha() {
        return senha;
    }

    public void setNome(Nome novoNome) {
        this.nome = novoNome;
    }

    public void setSenha(Senha novaSenha) {
        this.senha = novaSenha;
    }

}
