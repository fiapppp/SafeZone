package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "TIPORECOMPENSA")
public class TipoRecompensa extends PanacheEntity {
    @Column(name = "DESCRICAO", nullable = false)
    public String descricao;

    @Column(name = "STATUS")
    public Integer status;
}