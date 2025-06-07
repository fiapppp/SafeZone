package br.com.safezone.resource;

import br.com.safezone.dto.UsuarioResponseDTO;
import br.com.safezone.model.Localizacao;
import br.com.safezone.model.Perfil;
import br.com.safezone.model.Usuario;
import br.com.safezone.security.CurrentUser;
import br.com.safezone.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/api/usuario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    CurrentUser currentUser;

    /**
     * Cria um novo usuário (registro público), retornando DTO com perfil
     */
    @POST
    @Path("/criar")
    public Response criarUsuario(Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.criar(usuario);
            return Response.status(Response.Status.CREATED)
                    .entity(toDTO(novoUsuario))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Retorna os dados do usuário autenticado
     */
    @GET
    @Path("/me")
    @RolesAllowed({"cidadao", "funcionario", "admin"})
    public Response getPerfil() {
        Usuario usuario = currentUser.get();
        return Response.ok(toDTO(usuario)).build();
    }

    /**
     * Atualiza dados do próprio usuário autenticado
     */
    @PUT
    @Path("/me")
    @RolesAllowed({"cidadao", "funcionario", "admin"})
    public Response atualizarMeuPerfil(Usuario usuarioAtualizado) {
        Usuario logado = currentUser.get();
        Usuario atualizado = usuarioService.atualizar(logado.id, usuarioAtualizado);
        return Response.ok(toDTO(atualizado)).build();
    }

    /**
     * Lista todos os usuários (somente admin)
     */
    @GET
    @Path("/listar")
    @RolesAllowed("admin")
    public Response listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        List<UsuarioResponseDTO> dtos = usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return Response.ok(dtos).build();
    }

    /**
     * Busca usuário por ID (somente admin)
     */
    @GET
    @Path("/buscar/{id}")
    @RolesAllowed("admin")
    public Response buscarUsuarioPorId(@PathParam("id") Long id) {
        Usuario usuario = usuarioService.buscarPorId(id);
        return Response.ok(toDTO(usuario)).build();
    }

    /**
     * Atualiza outro usuário (somente admin)
     */
    @PUT
    @Path("/atualizar/{id}")
    public Response atualizarUsuario(@PathParam("id") Long id, Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizar(id, usuario);
            return Response.ok(toDTO(usuarioAtualizado)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    /**
     * Deleta usuário por ID (somente admin)
     */
    @DELETE
    @Path("/deletar/{id}")
    @RolesAllowed("admin")
    public Response deletarUsuario(@PathParam("id") Long id) {
        if (usuarioService.deletar(id)) {
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("Usuário com ID " + id + " não encontrado")
                .build();
    }

    /**
     * Converte entidade Usuario para DTO, recuperando Perfil,Localizacao do banco
     */
    private UsuarioResponseDTO toDTO(Usuario u) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.id = u.id;
        dto.nome = u.nome;
        dto.cpf = u.cpf;
        dto.email = u.email;
        dto.telefone = u.telefone;
        dto.dataNascimento = u.dataNascimento;
        dto.status = u.status;
        // Carrega Perfil completo a partir do ID
        Perfil perfil = Perfil.findById(u.perfil.id);
        dto.perfil = new UsuarioResponseDTO.PerfilInfo(
                perfil.id,
                perfil.nomePerfil
        );
        // Carrega Localizacao completa a partir do ID
        dto.localizacao = Localizacao.findById(u.localizacao.id);
        return dto;
    }
}
