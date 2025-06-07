package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "USUARIO")
public class Usuario extends PanacheEntity {

    @Column(name = "STATUS")
    public Integer status;

    @Column(name = "NOME", nullable = false)
    public String nome;

    @Column(name = "DATA_NASCIMENTO")
    public LocalDate dataNascimento;

    @Column(name = "EMAIL", nullable = false, unique = true)
    public String email;

    @Column(name = "SENHA", nullable = false)
    public String senha;

    @Column(name = "CPF", unique = true)
    public String cpf;

    @Column(name = "TELEFONE")
    public String telefone;

    @ManyToOne
    @JoinColumn(name = "ID_PERFIL")
    public Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "ID_LOCALIZACAO")
    public Localizacao localizacao;
}