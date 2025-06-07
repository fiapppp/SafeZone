package br.com.safezone.dto;

public class EvidenciaDTO {
    public Long id;
    public String tipoEvidencia;
    public String arquivo;

    public EvidenciaDTO() {}
    public EvidenciaDTO(Long id, String tipoEvidencia, String arquivo) {
        this.id = id;
        this.tipoEvidencia = tipoEvidencia;
        this.arquivo = arquivo;
    }
}