package com.sistema.entity;


import com.sistema.domain.Codigo;
import com.sistema.domain.Nome;
import com.sistema.domain.Perfil;

public class Carteira {

    private final Codigo codigo;
    private Nome nome;
    private Perfil perfil;
   
    public Carteira(Codigo codigo, Nome nome, Perfil perfil) {
        this.codigo = codigo;
        this.nome = nome;
        this.perfil = perfil;
    }

    public Codigo getCodigo() {
        return codigo;
    }

    public Nome getNome() {
        return nome;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setNome(Nome novoNome) {
        this.nome = novoNome;
    }

    public void setPerfil(Perfil novoPerfil) {
        this.perfil = novoPerfil;
    }
}
