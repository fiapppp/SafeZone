package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Resgate_Doacao")
public class ResgateDoacao extends PanacheEntity {
    @Column(name = "data_resgate", nullable = false)
    public LocalDate dataResgate;

    @Column(name = "status", nullable = false)
    public Integer status;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_doacao", nullable = false)
    public Doacao recompensa;
}
