package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Sessao")
public class Sessao extends PanacheEntity {
    @Column(unique = true, nullable = false)
    public String token;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    public Usuario usuario;

    @Column(name = "data_expiracao", nullable = false)
    public LocalDateTime dataExpiracao;
}