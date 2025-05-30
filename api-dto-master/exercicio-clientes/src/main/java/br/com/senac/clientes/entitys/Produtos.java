package br.com.senac.clientes.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Produtos extends EntidadeMaster{

    @Column(nullable = false)
    private String nome;

    private String descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
