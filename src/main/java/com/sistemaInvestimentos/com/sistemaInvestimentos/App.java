package com.sistemaInvestimentos;

import java.util.List;

import domain.CodNegociacao;
import domain.Codigo;
import domain.Cpf;
import domain.Data;
import domain.Nome;
import domain.Perfil;
import domain.Quantidade;
import domain.Senha;
import entity.Conta;
import entity.Ordem;
import service.AutenticacaoService;
import service.CarteiraService;
import service.ContaService;

import service.OrdemService;



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
     * AutenticacaoService authService = new AutenticacaoService();
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
     * }
     */

     public static void main(String[] args) {

        //criando usuario e autenticando
        AutenticacaoService autenticacaoService = new AutenticacaoService();
        ContaService contaService = new ContaService(autenticacaoService);
        
        
        Cpf cpf = new Cpf("05748668173");
        Nome nome = new Nome("joao");
        Senha senha = new Senha("Test#1");


        autenticacaoService.criarConta(cpf, nome, senha);
        autenticacaoService.autenticar(cpf, senha);

        
        System.out.println("Conta criada:\n" + contaService.visualizarConta());
        //criando carteiras e visualizando

        CarteiraService carteiraService = new CarteiraService(autenticacaoService);

        //carteira 1 
        Codigo codigoCarteira = new Codigo("00001");
        Nome nomeCarteira = new Nome("minha carteira");
        Perfil perfilCarteira = new Perfil("Moderado");

        carteiraService.criarCarteira(codigoCarteira, nomeCarteira, perfilCarteira);

        //carteira 2 
        Codigo codigoCarteira2 = new Codigo("00002");
        Nome nomeCarteira2 = new Nome("minha carteira 2");
        Perfil perfilCarteira2 = new Perfil("Moderado");

        carteiraService.criarCarteira(codigoCarteira2, nomeCarteira2, perfilCarteira2);

        //carteira 3
        Codigo codigoCarteira3 = new Codigo("00003");
        Nome nomeCarteira3 = new Nome("minha carteira 3");
        Perfil perfilCarteira3 = new Perfil("Moderado");

        carteiraService.criarCarteira(codigoCarteira3, nomeCarteira3, perfilCarteira3);
        
        //visualizar so a carteira 3
        System.out.println(carteiraService.visualizarCarteira(codigoCarteira3));
        
        //visualizar todas as carteiras
        //System.out.println(carteiraService.visualizarCarteiras());
        

        //criando ordens
        
        OrdemService ordemService = new OrdemService(autenticacaoService);

        //ordem 1 

        Codigo codigoOrdem1 = new Codigo("00001");
        CodNegociacao codNegociacaoOrdem1 = new CodNegociacao("IVVB11");
        Data dataOrdem1 = new Data("20250102");
        Quantidade quantidadeOrdem1 = new Quantidade(100);

        ordemService.criarOrdem(codigoOrdem1, codNegociacaoOrdem1, dataOrdem1, quantidadeOrdem1, codigoCarteira3);
        
        System.out.println(ordemService.visualizarOrdem(codigoCarteira3, codigoOrdem1));

        System.out.println(carteiraService.visualizarCarteira(codigoCarteira3));

        //ordem 2 

        Codigo codigoOrdem2 = new Codigo("00002");
        CodNegociacao codNegociacaoOrdem2 = new CodNegociacao("IVVB11T");
        Data dataOrdem2 = new Data("20250108");
        Quantidade quantidadeOrdem2 = new Quantidade(100);

        ordemService.criarOrdem(codigoOrdem2, codNegociacaoOrdem2, dataOrdem2, quantidadeOrdem2, codigoCarteira3);
        
        //ordem 3 

        Codigo codigoOrdem3 = new Codigo("00003");
        CodNegociacao codNegociacaoOrdem3 = new CodNegociacao("IVVB11T");
        Data dataOrdem3 = new Data("20250102");
        Quantidade quantidadeOrdem3 = new Quantidade(100);

        ordemService.criarOrdem(codigoOrdem3, codNegociacaoOrdem3, dataOrdem3, quantidadeOrdem3, codigoCarteira2);

        System.out.println(ordemService.visualizarOrdem(codigoCarteira3, codigoOrdem2));

        System.out.println(carteiraService.visualizarCarteira(codigoCarteira3));
        
        System.out.println("Conta criada:\n" + contaService.visualizarConta());
       
        //visualizar todas as carteiras
        System.out.println(carteiraService.visualizarCarteiras());
        Nome nomeNovo = new Nome("joao victor");
        contaService.editarNome(nomeNovo);

        System.out.println(contaService.visualizarConta());

      
    }
}