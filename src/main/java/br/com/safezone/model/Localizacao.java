package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "LOCALIZACAO")
public class Localizacao extends PanacheEntity {
    @Column(name = "NOME_ESTACAO", nullable = false)
    public String nomeEstacao;

    @Column(name = "LINHA")
    public String linha;

    @Column(name = "PLATAFORMA")
    public String plataforma;

    @Column(name = "LATITUDE_LONGITUDE")
    public String latitudeLongitude;

    @Column(name = "STATUS")
    public Integer status;
}