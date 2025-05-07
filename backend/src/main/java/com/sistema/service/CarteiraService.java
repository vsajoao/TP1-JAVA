package com.sistema.service;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;


import com.sistema.domain.Codigo;
import com.sistema.domain.Nome;
import com.sistema.domain.Perfil;
import com.sistema.entity.Conta;


public class CarteiraService {
    private final AutenticacaoService autenticacaoService;
    private Connection connection;

    

    public CarteiraService(AutenticacaoService autenticacaoService) {
        try {
            this.autenticacaoService = autenticacaoService;
            this.connection = DBService.getConnection();   
        } catch (SQLException e) {
            throw new RuntimeException("erro ao se conectar ao banco de dados");
        }
         
    }

    public void criarCarteira(Codigo codigo,Nome nome,Perfil perfil){
        Conta conta = autenticacaoService.getContaAutenticada();
        String sql = "INSERT INTO carteira(codigo,nome,perfil,cpf) VALUES (?, ?, ?, ?) ";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, codigo.getValor());
            stmt.setString(2, nome.getValor());
            stmt.setString(3, perfil.getValor());
            stmt.setString(4, conta.getCpf().getValor());

            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IllegalArgumentException("Codigo de carteira ja cadastrado");
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar carteira no banco de dados", e);
        }
    }

    public void atualizarNomeCarteira(Codigo codigo, Nome nome) {
        
        String sql = "UPDATE carteira SET nome = ? WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, nome.getValor());
            stmt.setString(2, codigo.getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao atualizar nome da carteira no banco de dados");
        }
    }

    public void atualizarPerfilCarteira(Codigo codigo, Perfil perfil) {
      
        String sql = "UPDATE carteira SET perfil = ? WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, perfil.getValor());
            stmt.setString(2, codigo.getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao atualizar perfil da carteira no banco de dados");
        }

    }

    public void excluirCarteira(Codigo codigo) {
        
        String sql = "DELETE FROM carteira WHERE codigo = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, codigo.getValor());
            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new RuntimeException("Nenhuma linha deletada no banco; código não encontrado: " + codigo.getValor());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar carteira do banco de dados", e);
        }
       
    }
   
}
