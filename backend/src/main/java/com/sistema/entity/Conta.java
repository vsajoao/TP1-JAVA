package com.sistema.entity;

import java.util.ArrayList;
import java.util.List;

import com.sistema.domain.Codigo;
import com.sistema.domain.Cpf;
import com.sistema.domain.Nome;
import com.sistema.domain.Senha;

public class Conta {
    private final Cpf cpf;
    private Nome nome;
    private Senha senha;
    private final List<Carteira> carteiras = new ArrayList<>();

    public List<Carteira> getCarteiras() {
        return new ArrayList<>(carteiras);
    }

    public Carteira obterCarteira(Codigo codigoCarteira) {
        return carteiras.stream()
                .filter(c -> c.getCodigo().equals(codigoCarteira))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
    }

    public void adicionarCarteira(Carteira carteira) {
        if (carteiras.size() >= 5) {
            throw new IllegalArgumentException("Máximo de 5 carteiras permitido por conta");
        }
        carteiras.add(carteira);
    }

    public void removerCarteira(Carteira carteira) {
        carteiras.remove(carteira);
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
