package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "USUARIO")
public class Usuario extends PanacheEntity {
    @Column(name = "EMAIL", nullable = false, unique = true)
    public String email;

    @Column(name = "SENHA", nullable = false)
    public String senha;

    @Column(name = "CPF", unique = true)
    public String cpf;

    @Column(name = "TELEFONE")
    public String telefone;

    @Column(name = "NOME", nullable = false)
    public String nome;

    @Column(name = "TIPO_CHAVE_PIX")
    public Integer tipoChavePix;

    @Column(name = "STATUS")
    public Integer status;

    @Column(name = "CHAVE_PIX", unique = true)
    public String chavePix;

    @Column(name = "PONTUACAO_TOTAL")
    public Integer pontuacaoTotal;

    @Column(name = "DATA_NASCIMENTO")
    public LocalDate dataNascimento;

    @ManyToOne
    @JoinColumn(name = "ID_PERFIL")
    public Perfil perfil;
}