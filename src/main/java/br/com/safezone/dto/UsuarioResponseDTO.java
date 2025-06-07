package br.com.safezone.dto;

import java.time.LocalDate;

public class UsuarioResponseDTO {
    public Long id;
    public String nome;
    public String email;
    public String telefone;
    public LocalDate dataNascimento;
    public Integer status;
    public PerfilInfo perfil;

    public static class PerfilInfo {
        public Long id;
        public String nomePerfil;

        public PerfilInfo() {}
        public PerfilInfo(Long id, String nomePerfil) {
            this.id = id;
            this.nomePerfil = nomePerfil;
        }
    }

    public UsuarioResponseDTO() {}
}
