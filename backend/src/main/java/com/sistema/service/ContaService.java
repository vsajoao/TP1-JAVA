package com.sistema.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.sistema.domain.Nome;
import com.sistema.domain.Senha;
import com.sistema.entity.Conta;

public class ContaService {
    private final AutenticacaoService autenticacaoService;
    private Connection connection;
    
    public ContaService(AutenticacaoService autenticacaoService) {
        
        try {
            this.autenticacaoService = autenticacaoService;
            this.connection = DBService.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao se conectar ao banco de dados");
        }
       
    }

    public void editarNome(Nome nome) {
        Conta conta = autenticacaoService.getContaAutenticada();
    
        String sql = "UPDATE conta SET nome = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome.getValor());
            stmt.setString(2, conta.getCpf().getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar nome no banco de dados", e);
        }
    }
    
    public void editarSenha(Senha senha) {
        Conta conta = autenticacaoService.getContaAutenticada();
       
        String sql = "UPDATE conta SET senha = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1,senha.getValor());
            stmt.setString(2, conta.getCpf().getValor());
            stmt.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException("Erro ao atualizar senha no banco de dados",e);
        }
    }

    public void excluirConta() {
        Conta conta = autenticacaoService.getContaAutenticada();
        if (!conta.getCarteiras().isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir conta com carteiras ativas");
        }
        autenticacaoService.logout();
        autenticacaoService.removerConta(conta.getCpf());
    }

  

}