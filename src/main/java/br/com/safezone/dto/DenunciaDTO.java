package br.com.safezone.dto;

import java.time.LocalDate;
import java.util.List;

public class DenunciaDTO {
    public String titulo;
    public String descricao;
    public LocalDate dataDenuncia;
    public Integer prioridade;
    public String CEP;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;
    public int numero;
    public Long idOcorrencia;
    public Long idCategoria;

    public DenunciaDTO() {}
}