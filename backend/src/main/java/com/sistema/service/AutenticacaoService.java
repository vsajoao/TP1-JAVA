package com.sistema.service;

import com.sistema.entity.Conta;
import com.sistema.domain.Cpf;
import com.sistema.domain.Nome;
import com.sistema.domain.Senha;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


public class AutenticacaoService {
   
    private Conta contaAutenticada = null;
    private Connection connection;

    public AutenticacaoService(){
        try {
            this.connection = DBService.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao se conectar ao banco de dados", e);
        }
        
    }

    public void criarConta(Cpf cpf, Nome nome, Senha senha) {
    String sql = "INSERT INTO conta (cpf, nome, senha) VALUES (?, ?, ?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, cpf.getValor());
        stmt.setString(2, nome.getValor());
        stmt.setString(3, senha.getValor()); // hash da senha???

        stmt.executeUpdate();
    } catch (SQLIntegrityConstraintViolationException e) {
        throw new IllegalArgumentException("CPF já cadastrado");
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erro ao criar conta no banco de dados", e);
    }
}

   public void autenticar(Cpf cpf, Senha senha) {
    String sql = "SELECT nome, senha FROM conta WHERE cpf = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, cpf.getValor());
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String senhaBanco = rs.getString("senha");
            if (!senhaBanco.equals(senha.getValor())) {
                throw new IllegalArgumentException("CPF ou senha inválidos");
            }

            Nome nome = new Nome(rs.getString("nome"));
            contaAutenticada = new Conta(cpf, nome, senha); 
        } else {
            throw new IllegalArgumentException("CPF ou senha inválidos");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Erro ao autenticar usuário", e);
    }
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
        String sql = "DELETE FROM conta WHERE cpf = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf.getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover conta", e);
        }
    }
    

}