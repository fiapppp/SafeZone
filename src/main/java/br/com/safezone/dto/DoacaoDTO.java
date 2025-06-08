package br.com.safezone.dto;

import br.com.safezone.model.Doacao;
import java.time.LocalDate;

public class DoacaoDTO {
    public Long id;
    public Integer custoPontos;
    public LocalDate dataValidade;
    public String descricao;
    public Integer quantidadeDisponivel;
    public Long tipoDoacaoId;
    public Integer status;

    public DoacaoDTO() {}

    public DoacaoDTO(Doacao r) {
        this.id = r.id;
        this.custoPontos = r.custoPontos;
        this.dataValidade = r.dataValidade;
        this.descricao = r.descricao;
        this.quantidadeDisponivel = r.quantidadeDisponivel;
        this.tipoDoacaoId = r.tipoDoacao.id;
        this.status = r.status;
    }
}