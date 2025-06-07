package br.com.safezone.model;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "EVIDENCIA")
public class Evidencia extends PanacheEntity {
    @Column(name = "TIPO_EVIDENCIA", nullable = false)
    public String tipoEvidencia;

    @Column(name = "ARQUIVO", nullable = false)
    public String arquivo;

    @ManyToOne
    @JoinColumn(name = "ID_DENUNCIA")
    public Denuncia denuncia;
}
