package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "ResgateRecompensa")
public class ResgateRecompensa extends PanacheEntity {
    @Column(name = "data_resgate", nullable = false)
    public LocalDate dataResgate;

    @Column(nullable = false)
    public Integer status;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_recompensa", nullable = false)
    public Recompensa recompensa;
}
