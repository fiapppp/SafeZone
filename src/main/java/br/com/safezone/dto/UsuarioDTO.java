package br.com.safezone.dto;

import br.com.safezone.model.Perfil;
import br.com.safezone.model.Usuario;

import java.time.LocalDate;

public class UsuarioDTO {
    public Long id;
    public int status;
    public String nome;
    public LocalDate dataNascimento;
    public String email;
    public String senha;
    public String telefone;
    public String cpf;
    public Perfil perfilId;
    public LocalizacaoDTO localizacao;

}
