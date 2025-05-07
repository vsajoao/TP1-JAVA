package com.sistema.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



import com.sistema.domain.CodNegociacao;
import com.sistema.domain.Data;
import com.sistema.domain.Codigo;
import com.sistema.domain.Quantidade;
import com.sistema.domain.Dinheiro;


public class OrdemService {
    private static final String ARQUIVO_PATH = "/home/jota/TP1-JAVA/backend/data/DADOS_HISTORICOS.txt";
    private final AutenticacaoService autenticacaoService;
    Connection connection;
    

    public OrdemService(AutenticacaoService autenticacaoService) {
        try {
            this.autenticacaoService = autenticacaoService;
            this.connection = DBService.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao se conectar com o banco de dados");
        }
        
    }


    public double obterPreco(CodNegociacao codigo, Data data) {
        String codPapel = codigo.getValor();
        String dataPapel = data.getValor();
        
        try (BufferedReader arquivo = new BufferedReader(new FileReader(ARQUIVO_PATH))) {
            
            String linha;
            while ((linha = arquivo.readLine()) != null) {
                
               
                if (linha.length() < 125) {
                    System.err.println("Linha inválida (tamanho insuficiente): " + linha);
                    continue;
                }
                
              
                String codigoLinha = linha.substring(12, 24).trim();
                if (!codigoLinha.equals(codPapel)) continue;
                
             
                String dataLinha = linha.substring(2, 10);
                if (!dataLinha.equals(dataPapel)) continue;
                
               
                String strPreco = linha.substring(112, 125).trim();
                try {
                    return Long.parseLong(strPreco) / 100.0;
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Formato de preço inválido na linha: " + linha, e);
                }
            }
            
            throw new RuntimeException("Registro não encontrado para código: " + codPapel + " e data: " + dataPapel);
            
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage(), e);
        }
    }

    public void criarOrdem(Codigo codigo,CodNegociacao cod_negociacao,Data data,Quantidade quantidade,Codigo codigo_carteira){
     

        double quant = quantidade.getValor();
        double preco = obterPreco(cod_negociacao, data);
        Dinheiro precoFinal = new Dinheiro(preco*quant);

        String sql = "INSERT INTO ordem(codigo,cod_negociacao,preco,data,quantidade,cod_carteira) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, codigo.getValor());
            stmt.setString(2, cod_negociacao.getValor());
            stmt.setString(3, precoFinal.toString());
            stmt.setString(4, data.getValor());
            stmt.setString(5, quantidade.getValorString());
            stmt.setString(6, codigo_carteira.getValor());

            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException("Erro ao criar ordem no banco de dados", e);
        }
    }
      
        
    

    public void excluirOrdem(Codigo codigo){

        String sql = "DELETE FROM ordem WHERE codigo =  ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, codigo.getValor());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao deletar ordem do banco de dados");
        }
    }

    
}