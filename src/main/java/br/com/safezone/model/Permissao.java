package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "PERMISSAO")
public class Permissao extends PanacheEntity {
    @Column(name = "IDENTIFICADOR", nullable = false)
    public String identificador;

    @Column(name = "CODIGO", nullable = false)
    public String codigo;

    @Column(name = "STATUS")
    public Integer status;
}