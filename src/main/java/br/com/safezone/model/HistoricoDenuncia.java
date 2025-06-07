package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "HISTORICODENUNCIA")
public class HistoricoDenuncia extends PanacheEntity {
    @Column(name = "DATA_ALTERACAO", nullable = false)
    public LocalDate dataAlteracao;

    @Column(name = "STATUS_ANTERIOR")
    public Integer statusAnterior;

    @Column(name = "STATUS_ATUAL")
    public Integer statusAtual;

    @Column(name = "ACAO")
    public String acao;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_DENUNCIA")
    public Denuncia denuncia;
}