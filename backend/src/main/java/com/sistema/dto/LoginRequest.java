// LoginRequest.java
package com.sistema.dto;
public class LoginRequest {
    private String cpf; // Tipo String, não Cpf
    private String senha; // Tipo String, não Senha

    // Getters e Setters para Strings
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}