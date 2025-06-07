package br.com.safezone.dto;

import br.com.safezone.model.Localizacao;

import java.time.LocalDate;

public class UsuarioResponseDTO {
    public Long id;
    public String nome;
    public String cpf;
    public String email;
    public String telefone;
    public LocalDate dataNascimento;
    public Integer status;
    public PerfilInfo perfil;
    public Localizacao localizacao;

    public static class PerfilInfo {
        public Long id;
        public String nomePerfil;

        public PerfilInfo(Long id, String nomePerfil) {
            this.id = id;
            this.nomePerfil = nomePerfil;
        }
    }

    public UsuarioResponseDTO() {}
}
