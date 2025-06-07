package br.com.safezone.dto;

public class LocalizacaoDTO {
    public Long id;
    public String nomeEstacao;
    public String linha;
    public String plataforma;
    public String latitudeLongitude;

    public LocalizacaoDTO() {}
    public LocalizacaoDTO(Long id, String nomeEstacao, String linha, String plataforma, String latitudeLongitude) {
        this.id = id;
        this.nomeEstacao = nomeEstacao;
        this.linha = linha;
        this.plataforma = plataforma;
        this.latitudeLongitude = latitudeLongitude;
    }
}