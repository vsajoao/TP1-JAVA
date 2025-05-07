package com.sistema;

import static spark.Spark.*;

import com.sistema.controller.AuthController;

public class App {
    public static void main(String[] args) {
        port(4567);

        // CORS
        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "http://localhost:3000");
            res.header("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });
        options("/*", (req, res) -> ""); // responde preflight

        // Rota de autenticação
        AuthController.routes();
    }
}