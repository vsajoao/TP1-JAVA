package com.sistemaInvestimentos;

import domain.CodNegociacao;
import domain.Codigo;
import domain.Cpf;
import domain.Data;
import domain.Nome;
import domain.Perfil;
import domain.Quantidade;
import domain.Senha;
import entity.Conta;
import service.AutenticacaoService;
import service.CarteiraService;
import service.ContaService;
import service.FileHistoricalDataService;
import service.HistoricalDataService;
import service.OrdemService;

//import java.sql.*;

public class App {
    /*
     * public static void main(String[] args) {
     * String url = "jdbc:mysql://localhost:3306/tp1_java";
     * String user = "appuser";
     * String pass = "AppUserSenha123";
     * 
     * try (Connection conn = DriverManager.getConnection(url, user, pass);
     * Statement st = conn.createStatement();
     * ResultSet rs = st.executeQuery("SELECT VERSION()")) {
     * 
     * if (rs.next()) {
     * System.out.println("Versão do MySQL: " + rs.getString(1));
     * }
     * } catch (SQLException e) {
     * e.printStackTrace();
     * }
     * }
     */
    public static void main(String[] args) {
        AutenticacaoService authService = new AutenticacaoService();
        HistoricalDataService dataService = new FileHistoricalDataService();
        OrdemService ordemService = new OrdemService(authService, dataService);
        CarteiraService carteiraService = new CarteiraService(authService);
        ContaService contaService = new ContaService(authService);

        Cpf meucpf = new Cpf("05748668173");
        Senha senha = new Senha("Test#1");
        Nome nome = new Nome("jao");
        Perfil perfil = new Perfil("Moderado");
        Codigo codigo = new Codigo("12345");

        // Autenticar usuário e criar ordem
        authService.criarConta(meucpf, nome, senha);
        authService.autenticar(meucpf, senha);

        carteiraService.criarCarteira(codigo, nome, perfil);

        ordemService.criarOrdem(
                new Codigo("00001"),
                new CodNegociacao("IVVB11"),
                new Data("20240101"),
                new Quantidade(100),
                new Codigo("12345"));
    }
}