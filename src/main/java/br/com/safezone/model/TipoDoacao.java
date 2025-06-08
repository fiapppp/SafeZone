package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TIPO_DOACAO")
public class TipoDoacao extends PanacheEntity {
    @Column(name = "TITULO", nullable = false)
    public String titulo;

    @Column(name = "DESCRICAO")
    public String descricao;

    @Column(name = "STATUS")
    public Integer status;
}