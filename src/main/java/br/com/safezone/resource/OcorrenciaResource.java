package br.com.safezone.resource;

import br.com.safezone.dto.CategoriaDTO;
import br.com.safezone.dto.LocalizacaoDTO;
import br.com.safezone.dto.OcorrenciaDTO;
import br.com.safezone.dto.UsuarioResponseDTO;
import br.com.safezone.exception.ApiException;
import br.com.safezone.model.*;
import br.com.safezone.service.OcorrenciaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/ocorrencia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OcorrenciaResource {

    @Inject
    OcorrenciaService ocorrenciaService;

    @GET
    @Path("/listar")
    public Response listarTodasOcorrencias() {
        try{
            List<OcorrenciaDTO> ocorrencias = ocorrenciaService.listarTodos();
            return Response.ok(ocorrencias).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/buscar/{id}")
    public Response buscarOcorrenciaPorId(@PathParam("id") Long id) {
        try{
            OcorrenciaDTO localizacao = ocorrenciaService.buscarPorIdCompleto(id);
            return Response.ok(localizacao).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @POST
    @Path("/criar")
    public Response criarOcorrencia(OcorrenciaDTO ocorrencia) {
        try{
            Ocorrencia nova = ocorrenciaService.criar(ocorrencia);
            return Response.status(Response.Status.CREATED).entity(toDTO(nova)).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    public Response atualizarOcorrencia(@PathParam("id") Long id, OcorrenciaDTO ocorrencia) {
        try{
            OcorrenciaDTO atualizada = new OcorrenciaDTO(ocorrenciaService.atualizar(id, ocorrencia));
            return Response.ok(atualizada).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("/deletar/{id}")
    public Response deletarOcorrencia(@PathParam("id") Long id) {
        try{
            if (ocorrenciaService.deletar(id)) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Ocorrencia com ID " + id + " não encontrada")
                    .build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }


    /**
     * Converte entidade Usuario para DTO, recuperando Perfil,Localizacao do banco
     */
    private OcorrenciaDTO toDTO(Ocorrencia u) {
        try{
            OcorrenciaDTO dto = new OcorrenciaDTO();
            dto.id = u.id;
            dto.status = u.status;
            dto.prioridade = u.prioridade;
            dto.titulo = u.descricao;
            dto.descricao = u.descricao;
            dto.raioAuxilio = u.raioAuxilio;

            // Carrega Categoria a partir do ID
            dto.categoria = new CategoriaDTO(Categoria.findById(u.categoria.id));
            // Carrega Localizacao a partir do ID
            dto.localizacao = new LocalizacaoDTO(Localizacao.findById(u.localizacao.id));
            return dto;
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

}