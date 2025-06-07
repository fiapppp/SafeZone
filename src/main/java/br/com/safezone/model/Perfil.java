package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "PERFIL")
public class Perfil extends PanacheEntity {
    @Column(name = "NOME_PERFIL", nullable = false, length = 255)
    public String nomePerfil;

    @Column(name = "STATUS", nullable = false)
    public Integer status;
}