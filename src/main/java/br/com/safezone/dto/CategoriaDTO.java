package br.com.safezone.dto;

public class CategoriaDTO {
    public Long id;
    public String descricao;

    public CategoriaDTO() {}
    public CategoriaDTO(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
}