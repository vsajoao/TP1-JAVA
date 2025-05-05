package service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import domain.CodNegociacao;
import domain.Data;
import domain.Dinheiro;
import domain.Quantidade;

public class FileHistoricalDataService implements HistoricalDataService {

    private static final String ARQUIVO_DADOS = "data/DADOS_HISTORICOS.TXT";
    private static final Path CAMINHO_ARQUIVO = Paths.get(ARQUIVO_DADOS);

    private static final int TAMANHO_CODIGO = 6;
    private static final int POS_INICIO_CODIGO = 17;

    private static final int TAMANHO_DATA = 8;
    private static final int POS_INICIO_DATA = 3;

    private static final int TAMANHO_PRECO = 13;
    private static final int POS_INICIO_PRECO = 133;

    // ... verificação de existência do arquivo ...

    @Override
    public boolean codigoPapelExiste(CodNegociacao codNegociacao) {
        try {

            return Files.lines(CAMINHO_ARQUIVO)
                    .anyMatch(linha -> extrairCodigo(linha).equals(codNegociacao.getValor().trim()));
        } catch (IOException e) {
            throw new IllegalArgumentException("Erro ao ler arquivo histórico", e);
        }
    }

    @Override
    public Dinheiro obterPrecoMedio(CodNegociacao cod, Data data, Quantidade qtd) {
        try {
            BigDecimal precoPorAcao = Files.lines(CAMINHO_ARQUIVO)
                    .filter(l -> correspondeCodigoEData(l, cod, data))
                    .findFirst()
                    .map(this::extrairPrecoBD)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Dados não encontrados para: " + cod.getValor() + " na data " + data.getValor()));

            // multiplica aqui, antes de criar o Dinheiro
            BigDecimal total = precoPorAcao
                    .multiply(BigDecimal.valueOf(qtd.getValor()))
                    .setScale(2, RoundingMode.HALF_UP);

            return new Dinheiro(total.doubleValue());
        } catch (IOException e) {
            throw new IllegalArgumentException("Erro ao acessar arquivo histórico", e);
        }
    }

    private String extrairCodigo(String linha) {
        return linha.substring(POS_INICIO_CODIGO, POS_INICIO_CODIGO + TAMANHO_CODIGO).trim();
    }

    private String extrairData(String linha) {
        return linha.substring(POS_INICIO_DATA, POS_INICIO_DATA + TAMANHO_DATA);
    }

    private BigDecimal extrairPrecoBD(String linha) {
        String campo = linha
                .substring(POS_INICIO_PRECO,
                        POS_INICIO_PRECO + TAMANHO_PRECO)
                .trim();

        if (!campo.matches("\\d+")) {
            throw new IllegalArgumentException("Preço inválido na linha: " + linha);
        }

        // 1) do centavos para reais, com 2 casas decimais:
        BigDecimal preco = new BigDecimal(campo)
                .movePointLeft(2) // equivalente a dividir por 100
                .setScale(2, RoundingMode.HALF_UP); // garante 2 casas decimais

        // 2) validação de faixa dentro do próprio serviço:
        BigDecimal min = new BigDecimal("0.01");
        BigDecimal max = new BigDecimal("1000000.00");
        if (preco.compareTo(min) < 0 || preco.compareTo(max) > 0) {
            throw new IllegalArgumentException(
                    String.format("Valor monetário inválido (deve ser entre R$ 0,01 e R$ 1.000.000,00): %s", preco));
        }

        return preco;
    }

    private boolean correspondeCodigoEData(String linha, CodNegociacao cod, Data data) {
        return extrairCodigo(linha).equals(cod.getValor().trim()) &&
                extrairData(linha).equals(data.getValor());
    }
}
