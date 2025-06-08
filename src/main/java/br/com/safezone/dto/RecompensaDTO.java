package br.com.safezone.dto;

import br.com.safezone.model.Recompensa;
import java.time.LocalDate;
import java.math.BigDecimal;

public class RecompensaDTO {
    public Long id;
    public Integer custoPontos;
    public LocalDate dataValidade;
    public String descricao;
    public Integer quantidadeDisponivel;
    public BigDecimal valor;
    public Long tipoRecompensaId;
    public Integer status;

    public RecompensaDTO() {}

    public RecompensaDTO(Recompensa r) {
        this.id = r.id;
        this.custoPontos = r.custoPontos;
        this.dataValidade = r.dataValidade;
        this.descricao = r.descricao;
        this.quantidadeDisponivel = r.quantidadeDisponivel;
        this.valor = r.valor;
        this.tipoRecompensaId = r.tipoRecompensa.id;
        this.status = r.status;
    }
}