package service;

import domain.Nome;
import domain.Senha;
import entity.Conta;

public class ContaService {
    private final AutenticacaoService autenticacaoService;

    public ContaService(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }

    public void editarNome(Nome novoNome) {
        Conta conta = autenticacaoService.getContaAutenticada();
        conta.setNome(novoNome);
    }

    public void editarSenha(Senha novaSenha) {
        Conta conta = autenticacaoService.getContaAutenticada();
        conta.setSenha(novaSenha);
    }

    public void excluirConta() {
        Conta conta = autenticacaoService.getContaAutenticada();
        if (!conta.getCarteiras().isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir conta com carteiras ativas");
        }
        autenticacaoService.logout();
        autenticacaoService.removerConta(conta.getCpf());
    }

    public String visualizarConta() {
        Conta conta = autenticacaoService.getContaAutenticada();
        return String.format(
                "CPF: %s\nNome: %s\nSaldo Total: R$ %.2f",
                conta.getCpf().getValor(),
                conta.getNome().getValor(),
                conta.getSaldoTotal());
    }

}