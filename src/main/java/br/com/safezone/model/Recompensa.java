package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Recompensa")
public class Recompensa extends PanacheEntity {
    @Column(name = "custo_pontos", nullable = false)
    public Integer custoPontos;

    @Column(name = "data_validade")
    public LocalDate dataValidade;

    @Column(name = "descricao", length = 4000, nullable = false)
    public String descricao;

    @Column(name = "quantidade_disponivel", nullable = false)
    public Integer quantidadeDisponivel;

    @Column(nullable = false)
    public java.math.BigDecimal valor;

    @Column(nullable = false)
    public Integer status;

    @ManyToOne
    @JoinColumn(name = "id_tipo_recompensa")
    public TipoRecompensa tipoRecompensa;

    @ManyToOne
    @JoinColumn(name = "id_parceiro")
    public Parceiro parceiro;
}