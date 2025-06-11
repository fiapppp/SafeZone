package br.com.safezone.dto;

import java.time.LocalDate;

public class ResgateResponseDTO {
    public Long id;
    public LocalDate dataResgate;
    public Integer status;
    public Long idUsuario;
    public Long idDoacao;
}