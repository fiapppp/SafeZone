package br.com.safezone.dto;

import br.com.safezone.model.Categoria;

public class CategoriaDTO {
    public Long id;
    public String nome;
    public String descricao;

    public CategoriaDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public CategoriaDTO(Categoria categoria) {
        this.id = categoria.id;
        this.nome = categoria.nome;
        this.descricao = categoria.descricao;
    }
}