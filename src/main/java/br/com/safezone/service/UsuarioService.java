package br.com.safezone.service;

import br.com.safezone.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
//import io.quarkus.elytron.security.common.BcryptUtil;

import java.util.List;

@ApplicationScoped
public class UsuarioService {

    public List<Usuario> listarTodos() {
        return Usuario.listAll();
    }

    public Usuario buscarPorId(Long id) {
        return Usuario.findByIdOptional(id)
                .map(u -> (Usuario) u)
                .orElseThrow(() -> new NotFoundException("Usuário com ID " + id + " não encontrado"));
    }

    @Transactional
    public Usuario criar(Usuario usuario) {
        if (usuario.nome == null || usuario.nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório");
        }
        if (usuario.email == null || usuario.email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email do usuário é obrigatório");
        }
        if (usuario.senha == null || usuario.senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha do usuário é obrigatória");
        }

        usuario.persist();
        return usuario;
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
