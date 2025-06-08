package br.com.safezone.service;

import br.com.safezone.dto.UsuarioDTO;
import br.com.safezone.model.Localizacao;
import br.com.safezone.model.Perfil;
import br.com.safezone.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
//import io.quarkus.elytron.security.common.BcryptUtil;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    @Inject
    LocalizacaoService localizacaoService;

    public List<Usuario> listarTodos() {
        return Usuario.listAll();
    }

    public Usuario buscarPorId(Long id) {
        return Usuario.findByIdOptional(id)
                .map(u -> (Usuario) u)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    @Transactional
    public Usuario criar(UsuarioDTO usuarioDto) {

        ValidaUsuario(usuarioDto);

        Usuario usuarioEntity = new Usuario();
        usuarioEntity.nome = usuarioDto.nome;
        usuarioEntity.dataNascimento = usuarioDto.dataNascimento;
        usuarioEntity.email = usuarioDto.email;
        usuarioEntity.senha = usuarioDto.senha;
        usuarioEntity.cpf = usuarioDto.cpf;
        usuarioEntity.telefone = usuarioDto.telefone;
        usuarioEntity.perfil = Perfil.findById(usuarioDto.perfilId);

        Localizacao localizacao = new Localizacao();
        localizacao.CEP = usuarioDto.localizacao.cep;
        localizacao.logradouro = usuarioDto.localizacao.logradouro;
        localizacao.bairro = usuarioDto.localizacao.bairro;
        localizacao.cidade = usuarioDto.localizacao.cidade;
        localizacao.estado = usuarioDto.localizacao.estado;
        localizacao.numero = usuarioDto.localizacao.numero;
        Localizacao localizacaoCriada = localizacaoService.criar(localizacao);

        usuarioEntity.localizacao = localizacaoCriada;

        usuarioEntity.persist();

        return usuarioEntity;
    }

    public void ValidaUsuario(UsuarioDTO usuarioDto) {
        if (usuarioDto.nome == null || usuarioDto.nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        if (usuarioDto.email == null || usuarioDto.email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email do usuário é obrigatório");
        }
        if (usuarioDto.senha == null || usuarioDto.senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha do usuário é obrigatória");
        }
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = buscarPorId(id);

        usuarioExistente.nome = usuarioAtualizado.nome;
        usuarioExistente.email = usuarioAtualizado.email;

        // Atualiza a senha somente se for enviada
        if (usuarioAtualizado.senha != null && !usuarioAtualizado.senha.trim().isEmpty()) {
            usuarioExistente.senha = usuarioAtualizado.senha;
        }

        usuarioExistente.telefone = usuarioAtualizado.telefone;
        usuarioExistente.dataNascimento = usuarioAtualizado.dataNascimento;
        usuarioExistente.status = usuarioAtualizado.status;

        return usuarioExistente;
    }

    @Transactional
    public boolean deletar(Long id) {
        return Usuario.deleteById(id);
    }

    public Usuario buscarPorEmail(String email) {
        return Usuario.find("email", email).firstResult();
    }
}
