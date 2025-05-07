package com.sistema.dto;

public class LoginResponse {
    private String cpf;
    private String nome;
    private String mensagem;

    public LoginResponse(String cpf, String nome, String mensagem) {
        this.cpf = cpf;
        this.nome = nome;
        this.mensagem = mensagem;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getMensagem() {
        return mensagem;
    }
}
