package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "GRUPODENUNCIA")
public class GrupoDenuncia extends PanacheEntity {
    @Column(name = "DESCRICAO_GRUPO", nullable = false)
    public String descricaoGrupo;

    @Column(name = "STATUS")
    public Integer status;

    @Column(name = "DATA_CRIACAO", nullable = false)
    public LocalDate dataCriacao;

    @Column(name = "OBSERVACAO_RESPONSAVEL")
    public String observacaoResponsavel;

    @Column(name = "PRIORIDADE")
    public Integer prioridade;

    @Column(name = "ID_RESPONSAVEL")
    public Long idResponsavel;
}

