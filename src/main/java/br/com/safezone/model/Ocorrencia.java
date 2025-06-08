package br.com.safezone.model;
import br.com.safezone.dto.OcorrenciaDTO;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;


@Entity
@Table(name = "OCORRENCIA")
public class Ocorrencia extends PanacheEntity {

    @Column(name = "STATUS")
    public Integer status;

    @Column(name = "PRIORIDADE")
    public Integer prioridade;

    @Column(name = "DATA_CRIACAO", nullable = false)
    public LocalDate dataCriacao;

    @Column(name = "TITULO", nullable = false)
    public String titulo;

    @Column(name = "DESCRICAO")
    public String descricao;

    @Column(name = "RAIO_AUXILIO")
    public String raioAuxilio;

    @ManyToOne
    @JoinColumn(name = "ID_LOCALIZACAO")
    public Localizacao localizacao;

    @ManyToOne
    @JoinColumn(name = "ID_CATEGORIA")
    public Categoria categoria;

}


