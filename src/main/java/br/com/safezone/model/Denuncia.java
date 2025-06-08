package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Denuncia")
public class Denuncia extends PanacheEntity {

    @Column(name = "data_denuncia", nullable = false)
    public LocalDate dataDenuncia;

    @Column(name = "data_conclusao")
    public LocalDate dataConclusao;

    /**
     * 0 = aberto, 1 = em análise, 2 = validada, 3 = rejeitada
     */
    @Column(name = "status", nullable = false)
    public Integer status;

    @Column(name = "prioridade", nullable = false)
    public Integer prioridade;

    @Column(name = "titulo", nullable = false, length = 255)
    public String titulo;

    @Column(name = "descricao", length = 4000)
    public String descricao;

    @Column(name = "observacao_responsavel", length = 4000)
    public String observacaoResponsavel;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cidadao_prejudicado")
    public Usuario cidadao_prejudicado;

    @ManyToOne
    @JoinColumn(name = "id_cidadao_apoidador")
    public Usuario cidadao_apoidador;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    public Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_ocorrencia")
    public Ocorrencia ocorrencia;

    @ManyToOne
    @JoinColumn(name = "id_localizacao")
    public Localizacao localizacao;
}