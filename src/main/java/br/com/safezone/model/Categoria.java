package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIA")
public class Categoria extends PanacheEntity {
    @Column(name = "STATUS")
    public Integer status;

    @Column(name = "NOME", nullable = false)
    public String nome;

    @Column(name = "DESCRICAO")
    public String descricao;
}