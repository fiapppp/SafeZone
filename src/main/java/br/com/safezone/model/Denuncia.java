package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Denuncia")
public class Denuncia extends PanacheEntity {

    @Column(name = "data_denuncia", nullable = false)
    public LocalDate dataDenuncia;

    @Column(nullable = false, length = 4000)
    public String descricao;

    /**
     * 0 = aberto, 1 = em análise, 2 = validada, 3 = rejeitada
     */
    @Column(nullable = false)
    public Integer status;

    @Column(name = "data_conclusao")
    public LocalDate dataConclusao;

    @Column(name = "observacao_responsavel", length = 4000)
    public String observacaoResponsavel;

    @Column(nullable = false)
    public Integer prioridade;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    public Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "id_grupoDenuncia")
    public Ocorrencia grupoDenuncia;

    @ManyToOne
    @JoinColumn(name = "id_localizacao")
    public Localizacao localizacao;

    @Column(name = "informacao_adicional", length = 4000)
    public String informacaoAdicional;
}