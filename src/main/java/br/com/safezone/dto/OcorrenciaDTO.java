package br.com.safezone.dto;

import br.com.safezone.model.Categoria;
import br.com.safezone.model.Localizacao;
import br.com.safezone.model.Ocorrencia;

import java.time.LocalDate;

public class OcorrenciaDTO {
    public long id;
    public Integer status;
    public int prioridade;
    public String titulo;
    public String descricao;
    public String raioAuxilio;
    public LocalizacaoDTO localizacao;
    public CategoriaDTO categoria;

    public OcorrenciaDTO() {}
    public OcorrenciaDTO(Ocorrencia ocorrencia) {
        this.id = ocorrencia.id;
        this.status = ocorrencia.status;
        this.titulo = ocorrencia.titulo;
        this.descricao = ocorrencia.descricao;
        this.prioridade = ocorrencia.prioridade;
        this.raioAuxilio = ocorrencia.raioAuxilio;
        this.localizacao = new LocalizacaoDTO(Localizacao.findById(ocorrencia.localizacao.id));
        this.categoria = new CategoriaDTO(Categoria.findById(ocorrencia.categoria.id));
    }
}
