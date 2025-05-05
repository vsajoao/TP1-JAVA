package service;

import entity.Ordem;
import entity.Carteira;
import domain.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemService {

    private final List<Ordem> ordens = new ArrayList<>();
    private final AutenticacaoService autenticacaoService;
    private final HistoricalDataService dataService;

    // 1. Corrigir injeção de dependência
    public OrdemService(AutenticacaoService autenticacaoService,
            HistoricalDataService dataService) {
        this.autenticacaoService = autenticacaoService;
        this.dataService = dataService;
    }

    public void criarOrdem(Codigo codigoOrdem, CodNegociacao codigoPapel,
            Data data, Quantidade quantidade, Codigo codigoCarteira) {

        // 2. Validar código único primeiro
        validarCodigoUnico(codigoOrdem);

        // 3. Buscar carteira na conta autenticada
        Carteira carteira = autenticacaoService.getContaAutenticada()
                .getCarteiras().stream()
                .filter(c -> c.getCodigo().equals(codigoCarteira))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada"));

        // 4. Validar código do papel
        if (!dataService.codigoPapelExiste(codigoPapel)) {
            throw new IllegalArgumentException("Papel não encontrado: " + codigoPapel.getValor());
        }

        // 5. Calcular preço total
        Dinheiro precoTotal = dataService.obterPrecoMedio(codigoPapel, data, quantidade);

        // 6. Criar e armazenar a ordem
        Ordem novaOrdem = new Ordem(
                codigoOrdem,
                codigoPapel,
                data,
                quantidade,
                precoTotal,
                carteira);

        ordens.add(novaOrdem);
        carteira.adicionarOrdem(novaOrdem);
    }

    public void excluirOrdem(Codigo codigoOrdem) {
        Ordem ordem = buscarOrdem(codigoOrdem)
                .orElseThrow(() -> new IllegalArgumentException("Ordem não encontrada: " + codigoOrdem.getValor()));

        // 7. Validar associações antes de excluir
        if (!ordem.getCarteira().getOrdens().contains(ordem)) {
            throw new IllegalArgumentException("Ordem não está associada à carteira");
        }

        ordens.remove(ordem);
        ordem.getCarteira().removerOrdem(ordem);
    }

    // 8. Método auxiliar melhorado
    private Optional<Ordem> buscarOrdem(Codigo codigo) {
        return ordens.stream()
                .filter(o -> o.getCodigo().equals(codigo))
                .findFirst();
    }

    // 9. Validação de código único
    private void validarCodigoUnico(Codigo codigo) {
        if (buscarOrdem(codigo).isPresent()) {
            throw new IllegalArgumentException("Código de ordem já existe: " + codigo.getValor());
        }
    }

    // 10. Listagem com segurança
    public List<Ordem> listarOrdensPorCarteira(Codigo codigoCarteira) {
        return autenticacaoService.getContaAutenticada()
                .getCarteiras().stream()
                .filter(c -> c.getCodigo().equals(codigoCarteira))
                .findFirst()
                .map(Carteira::getOrdens)
                .orElseThrow(() -> new IllegalArgumentException("Carteira não encontrada"));
    }
}