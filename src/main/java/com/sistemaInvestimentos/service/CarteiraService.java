package service;


import java.util.List;

import domain.Codigo;
import domain.Nome;
import domain.Perfil;
import entity.Carteira;
import entity.Conta;
import entity.Ordem;

public class CarteiraService {
    private final AutenticacaoService autenticacaoService;

    public CarteiraService(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    public void criarCarteira(Codigo codigo, Nome nome, Perfil perfil) {
        Conta conta = autenticacaoService.getContaAutenticada();
        
        boolean existe = conta.getCarteiras().stream()
                .anyMatch(c -> c.getCodigo().equals(codigo));
        if (existe) {
            throw new IllegalArgumentException(
                "Já existe uma carteira com o código informado: " + codigo.getValor()
            );
        }
        
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

    public String visualizarCarteiras() {
        Conta conta = autenticacaoService.getContaAutenticada();
        StringBuilder sb = new StringBuilder();
        sb.append("=== Carteiras da Conta ===\n");
        
        if (conta.getCarteiras().isEmpty()) {
            sb.append("Nenhuma carteira cadastrada.\n");
            return sb.toString();
        }
        
        for (Carteira c : conta.getCarteiras()) {
            sb.append(String.format(
                "Código: %s\n" +
                "Nome  : %s\n" +
                "Perfil: %s\n" +
                "Saldo : R$ %.2f\n" +
                "Ordens: %d\n" +
                "--------------------------\n",
                c.getCodigo().getValor(),
                c.getNome().getValor(),
                c.getPerfil().getValor(),
                c.getSaldo(),
                c.getOrdens().size()
            ));
        }
        
        return sb.toString();
    }

    public String visualizarCarteira(Codigo codigoCarteira) {
        Conta conta = autenticacaoService.getContaAutenticada();
        // Busca a carteira pelo código fornecido
        Carteira carteira = conta.obterCarteira(codigoCarteira);
        StringBuilder sb = new StringBuilder();
        sb.append("=== Carteira " + codigoCarteira.getValor() + " ===\n");
        sb.append(String.format(
            "Código: %s\n" +
            "Nome  : %s\n" +
            "Perfil: %s\n" +
            "Saldo : R$ %.2f\n" +
            "Ordens: %d\n",
            carteira.getCodigo().getValor(),
            carteira.getNome().getValor(),
            carteira.getPerfil().getValor(),
            carteira.getSaldo(),
            carteira.getOrdens().size()
        ));
        return sb.toString();
    }

    public List<Ordem> getOrdensCarteira(Codigo codigoCarteira) {
        Conta conta = autenticacaoService.getContaAutenticada();
        // Busca a carteira pelo código fornecido
        Carteira carteira = conta.obterCarteira(codigoCarteira);
        return carteira.getOrdens();
    }
}
