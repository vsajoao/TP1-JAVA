package entity;

import domain.*;

public class Ordem {
    private final Codigo codigo;
    private final CodNegociacao codNegociacao;
    private final Data data;
    private final Quantidade quantidade;
    private final Dinheiro preco;
    private final Codigo codigoCarteira;
   

    public Ordem(Codigo codigo, CodNegociacao codNegociacao, Data data, Quantidade quantidade, Dinheiro preco,Codigo codigoCarteira
            ) {
        this.codigo = codigo;
        this.codNegociacao = codNegociacao;
        this.data = data;
        this.quantidade = quantidade;
        this.preco = preco;
        this.codigoCarteira = codigoCarteira;
        
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public CodNegociacao getCodNegociacao() {
        return codNegociacao;
    }

    public Data getData() {
        return data;
    }

    public Quantidade getQuantidade() {
        return quantidade;
    }

    public Dinheiro getDinheiro() {
        return preco;
    }

    public Codigo getCodigoCarteira(){
        return codigoCarteira;
    }
    
}
