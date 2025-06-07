package br.com.safezone.dto;

import java.time.LocalDate;
import java.util.List;

public class DenunciaDTO {
    public LocalDate dataDenuncia;
    public String descricao;
    public String informacaoAdicional;
    public Long idCategoria;
    public Long idLocalizacao;
    public Integer prioridade;
    public List<EvidenciaDTO> evidencia;

    public DenunciaDTO() {}
}