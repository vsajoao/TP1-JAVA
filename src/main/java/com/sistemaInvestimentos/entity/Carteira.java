package entity;

import java.util.ArrayList;
import java.util.List;

import domain.Codigo;
import domain.Nome;
import domain.Perfil;

public class Carteira {

    private final Codigo codigo;
    private Nome nome;
    private Perfil perfil;
    private List<Ordem> ordens = new ArrayList<>();

    public double getSaldo() {
        return ordens.stream()
                .mapToDouble(o -> o.getDinheiro().getValor())
                .sum();
    }

    public void adicionarOrdem(Ordem ordem) {
        ordens.add(ordem);
    }

    public void removerOrdem(Ordem ordem) {
        ordens.remove(ordem);
    }

    public List<Ordem> getOrdens() {
        return new ArrayList<>(ordens);
    }

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
