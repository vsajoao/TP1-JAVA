package com.sistema.controller;

import static spark.Spark.*;

import com.sistema.service.AutenticacaoService;
import com.sistema.domain.Cpf;
import com.sistema.domain.Senha;
import com.sistema.dto.LoginRequest;
import com.sistema.dto.LoginResponse;
import com.sistema.entity.Conta;
import com.google.gson.Gson;

public class AuthController {
    private static final AutenticacaoService authService = new AutenticacaoService();
    private static final Gson gson = new Gson();

    public static void routes() {
        path("/api/auth", () -> {
            post("/login", (req, res) -> {
                res.type("application/json");

                try {
                    LoginRequest login = gson.fromJson(req.body(), LoginRequest.class);
        
                    // Converta para objetos de domínio com validação
                        Cpf cpf = new Cpf(login.getCpf());
                        Senha senha = new Senha(login.getSenha());
        
                            authService.autenticar(cpf, senha);
                    Conta conta = authService.getContaAutenticada();

                    // Resposta em caso de sucesso
                    LoginResponse response = new LoginResponse(
                        conta.getCpf().getValor(),
                        conta.getNome().getValor(),
                        "Autenticado com sucesso"
                    );

                    res.status(200);
                    return gson.toJson(response);

                } catch (Exception e) {
                    res.status(401);
                    return gson.toJson(new LoginResponse(null, null, "Credenciais inválidas: " + e.getMessage()));
                }
            });
        });
    }
}
