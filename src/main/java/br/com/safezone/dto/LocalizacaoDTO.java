package br.com.safezone.dto;

import br.com.safezone.model.Localizacao;

public class LocalizacaoDTO {
    public Long id;
    public String logradouro;
    public String bairro;
    public String cidade;
    public String estado;
    public String cep;
    public int numero;
    public String latitudeLongitude;

    public LocalizacaoDTO() {}
    public LocalizacaoDTO(Localizacao localizacao) {
        this.id = localizacao.id;
        this.logradouro = localizacao.logradouro;
        this.bairro = localizacao.bairro;
        this.cidade = localizacao.cidade;
        this.estado = localizacao.estado;
        this.cep = localizacao.CEP;
        this.numero = localizacao.numero;
        this.latitudeLongitude = localizacao.latitudeLongitude;
    }

    public LocalizacaoDTO(Long id, String logradouro, String bairro, String cidade, String estado, String cep, int numero, String latitudeLongitude) {
        this.id = id;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
        this.numero = numero;
        this.latitudeLongitude = latitudeLongitude;
    }
}