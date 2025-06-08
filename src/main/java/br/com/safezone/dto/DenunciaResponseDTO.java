package br.com.safezone.dto;

import java.time.LocalDate;
import java.util.List;

public class DenunciaResponseDTO {
    public Long id;
    public LocalDate dataDenuncia;
    public String descricao;
    public Integer status;
    public LocalDate dataConclusao;
    public Integer prioridade;
    public CategoriaDTO categoria;
    public LocalizacaoDTO localizacao;

    public DenunciaResponseDTO() {}
}