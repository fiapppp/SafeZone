package br.com.safezone.dto;

public class CategoriaDTO {
    public Long id;
    public String nome;
    public String descricao;

    public CategoriaDTO(Long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }
}