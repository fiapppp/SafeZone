package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "DOACAO")
public class Doacao extends PanacheEntity {
    @Column(nullable = false)
    public Integer status;

    @Column(name = "titulo", nullable = false)
    public String titulo;

    @Column(name = "descricao", length = 4000)
    public String descricao;

    @Column(name = "custo_pontos", nullable = false)
    public Integer custoPontos;

    @Column(name = "data_validade")
    public LocalDate dataValidade;

    @Column(name = "habilitado_conversao")
    public Integer habilitadoConversao;

    @Column(name = "quantidade_disponivel", nullable = false)
    public Integer quantidadeDisponivel;

    @Column(name = "quantidade_conversao")
    public Integer quantidadeConversao;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doacao")
    public TipoDoacao tipoDoacao;
}