package br.com.safezone.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "Parceiro")
public class Parceiro extends PanacheEntity {
    @Column(name = "nome_parceiro", nullable = false)
    public String nomeParceiro;

    @Column(nullable = false)
    public String contato;

    @Column(unique = true)
    public String cnpj;

    @Column(nullable = false)
    public Integer status; // 0=inativo,1=ativo
}