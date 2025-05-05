package service;

import entity.Conta;
import domain.Cpf;
import domain.Nome;
import domain.Senha;
import java.util.HashMap;
import java.util.Map;

public class AutenticacaoService {
    private final Map<Cpf, Conta> contas = new HashMap<>();
    private Conta contaAutenticada = null;

    public void criarConta(Cpf cpf, Nome nome, Senha senha) {
        if (contas.containsKey(cpf)) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }
        contas.put(cpf, new Conta(cpf, nome, senha));
    }

    public void autenticar(Cpf cpf, Senha senha) {
        Conta conta = contas.get(cpf);
        if (conta == null || !conta.getSenha().equals(senha)) {
            throw new IllegalArgumentException("CPF ou senha inválidos");
        }
        contaAutenticada = conta;
    }

    public void logout() {
        contaAutenticada = null;
    }

    public Conta getContaAutenticada() {
        if (contaAutenticada == null) {
            throw new IllegalArgumentException("Nenhum usuário autenticado");
        }
        return contaAutenticada;
    }

    public void removerConta(Cpf cpf) {
        contas.remove(cpf);
    }

}