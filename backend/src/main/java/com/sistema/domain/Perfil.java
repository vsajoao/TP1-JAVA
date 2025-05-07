package com.sistema.domain;

public class Perfil {
    public String perfilString;

    public Perfil(String perfilString) {
        setPerfil(perfilString);
    }

    public String getValor() {
        return perfilString;
    }

    private void setPerfil(String perfilString) {
        if (perfilString == null ||
                !(perfilString.equalsIgnoreCase("Conservador") ||
                        perfilString.equalsIgnoreCase("Moderado") ||
                        perfilString.equalsIgnoreCase("Agressivo"))) {
            throw new IllegalArgumentException("Perfil inválido. Deve ser Conservador, Moderado ou Agressivo.");
        }
        this.perfilString = perfilString;
    }
}
