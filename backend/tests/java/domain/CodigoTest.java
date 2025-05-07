package tests.java.domain;

import org.junit.jupiter.api.Test;

import domain.Codigo;

import static org.junit.jupiter.api.Assertions.*;

class CodigoTest {

    @Test
    void deveCriarCodigoCom5DigitosValidos() {
        // Cenário válido
        assertDoesNotThrow(() -> new Codigo("12345"),
                "Deveria aceitar código com 5 dígitos");
    }

    @Test
    void deveLancarExcecaoParaCodigoCom4Digitos() {
        // Cenário inválido - tamanho menor
        assertThrows(IllegalArgumentException.class,
                () -> new Codigo("1234"),
                "Deveria rejeitar código com menos de 5 dígitos");
    }

    @Test
    void deveLancarExcecaoParaCodigoCom6Digitos() {
        // Cenário inválido - tamanho maior
        assertThrows(IllegalArgumentException.class,
                () -> new Codigo("123456"),
                "Deveria rejeitar código com mais de 5 dígitos");
    }

    @Test
    void deveLancarExcecaoParaCodigoComLetras() {
        // Cenário inválido - caracteres não numéricos
        assertThrows(IllegalArgumentException.class,
                () -> new Codigo("12A45"),
                "Deveria rejeitar código contendo letras");
    }

    @Test
    void deveLancarExcecaoParaCodigoVazio() {
        // Cenário inválido - string vazia
        assertThrows(IllegalArgumentException.class,
                () -> new Codigo(""),
                "Deveria rejeitar código vazio");
    }

    @Test
    void deveLancarExcecaoParaCodigoNulo() {
        // Cenário inválido - valor nulo
        assertThrows(IllegalArgumentException.class,
                () -> new Codigo(null),
                "Deveria rejeitar código nulo");
    }

    @Test
    void deveManterValorCorretamente() {
        // Teste de armazenamento correto do valor
        Codigo codigo = new Codigo("98765");
        assertEquals("98765", codigo.getValor(),
                "Deveria armazenar o valor corretamente");
    }
}