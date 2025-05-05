package service;

import domain.CodNegociacao;
import domain.Data;
import domain.Dinheiro;
import domain.Quantidade;

public interface HistoricalDataService {
    boolean codigoPapelExiste(CodNegociacao codigo);

    Dinheiro obterPrecoMedio(CodNegociacao codigo, Data data, Quantidade quantidade);
}