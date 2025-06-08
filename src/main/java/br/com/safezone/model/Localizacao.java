package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "LOCALIZACAO")
public class Localizacao extends PanacheEntity {
    @Column(name = "STATUS")
    public Integer status;

    @Column(name = "LOGRADOURO")
    public String logradouro;

    @Column(name = "BAIRRO")
    public String bairro;

    @Column(name = "CIDADE")
    public String cidade;

    @Column(name = "ESTADO")
    public String estado;

    @Column(name = "CEP")
    public String CEP;

    @Column(name = "NUMERO")
    public Integer numero;

    @Column(name = "LATITUDE_LONGITUDE")
    public String latitudeLongitude;
}