package service;

import domain.Codigo;
import domain.Nome;
import domain.Perfil;
import entity.Carteira;
import entity.Conta;

public class CarteiraService {
    private final AutenticacaoService autenticacaoService;

    public CarteiraService(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    public void criarCarteira(Codigo codigo, Nome nome, Perfil perfil) {
        Conta conta = autenticacaoService.getContaAutenticada();
        Carteira novaCarteira = new Carteira(codigo, nome, perfil);
        conta.adicionarCarteira(novaCarteira);
    }

    public void atualizarNomeCarteira(Codigo codigo, Nome nome) {
        Conta conta = autenticacaoService.getContaAutenticada();
        Carteira carteira = conta.obterCarteira(codigo);
        carteira.setNome(nome);
    }

    public void atualizarPerfilCarteira(Codigo codigo, Perfil perfil) {
        Conta conta = autenticacaoService.getContaAutenticada();
        Carteira carteira = conta.obterCarteira(codigo);
        carteira.setPerfil(perfil);
    }

    public void excluirCarteira(Codigo codigo) {
        Conta conta = autenticacaoService.getContaAutenticada();
        Carteira carteira = conta.getCarteiras().stream()
                .filter(c -> c.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada"));

        if (!carteira.getOrdens().isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir carteira com ordens ativas");
        }

        conta.removerCarteira(carteira);
    }

}
