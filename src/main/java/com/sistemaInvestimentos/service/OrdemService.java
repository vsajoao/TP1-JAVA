package service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


import domain.*;
import entity.Carteira;
import entity.Conta;
import entity.Ordem;

public class OrdemService {
    private static final String ARQUIVO_PATH = "/home/jota/TP1-JAVA/data/DADOS_HISTORICOS.txt";
    private final AutenticacaoService autenticacaoService;
    

    public OrdemService(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
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

    public void criarOrdem(Codigo codigoOrdem,CodNegociacao codNegociacao,Data data,Quantidade quantidade,Codigo codigoCarteira){
        Conta conta = autenticacaoService.getContaAutenticada();
        Carteira carteira = conta.obterCarteira(codigoCarteira);
        

        double quant = quantidade.getValor();
        double preco = obterPreco(codNegociacao, data);
        Dinheiro precoFinal = new Dinheiro(preco*quant);

        Ordem ordem = new Ordem(codigoOrdem, codNegociacao, data, quantidade, precoFinal,codigoCarteira);
        carteira.adicionarOrdem(ordem);
    }

    public void excluirOrdem(Ordem ordem,Codigo codigoCarteira){
        Conta conta = autenticacaoService.getContaAutenticada();
        Carteira carteira = conta.obterCarteira(codigoCarteira);

        carteira.removerOrdem(ordem);
    }

    public String visualizarOrdem(Codigo codigoCarteira, Codigo codigoOrdem) {
        Conta conta = autenticacaoService.getContaAutenticada();
        Carteira carteira = conta.obterCarteira(codigoCarteira);
        Ordem ordem = carteira.getOrdens().stream()
                .filter(o -> o.getCodigo().equals(codigoOrdem))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Ordem não encontrada na carteira " + codigoCarteira.getValor()
                ));
    
        // Imprime os valores conforme estão armazenados
        StringBuilder sb = new StringBuilder();
        sb.append("=== Ordem " + codigoOrdem.getValor() + " ===\n");
        sb.append(String.format(
                "Código da Ordem : %s\n" +
                "Negociação      : %s\n" +
                "Data            : %s\n" +
                "Quantidade      : %s\n" +
                "Preço Final     : %s\n" +
                "Carteira        : %s\n",
                ordem.getCodigo().getValor(),
                ordem.getCodNegociacao().getValor(),
                ordem.getData().getValor(),
                ordem.getQuantidade().getValor(),
                ordem.getDinheiro().getValor(),
                ordem.getCodigoCarteira().getValor()
        ));
        return sb.toString();
    }
    
    
}