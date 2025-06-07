package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "PONTUACAO")
public class Pontuacao extends PanacheEntity {
    @Column(name = "VALOR_PONTOS", nullable = false)
    public Integer valorPontos;

    @Column(name = "DATA_CONCESSAO", nullable = false)
    public LocalDate dataConcessao;

    @Column(name = "STATUS")
    public Integer status;

    @Column(name = "DATA_ATUALIZACAO")
    public LocalDate dataAtualizacao;

    @ManyToOne
    @JoinColumn(name = "ID_DENUNCIA", nullable = false)
    public Denuncia denuncia;
}
