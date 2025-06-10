package br.com.safezone.resource;

import br.com.safezone.exception.ApiException;
import br.com.safezone.model.Localizacao;
import br.com.safezone.service.LocalizacaoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/localizacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocalizacaoResource {

    @Inject
    LocalizacaoService localizacaoService;

    @GET
    @Path("/listar")
    public Response listarTodasLocalizacoes() {
        try {
            List<Localizacao> localizacoes = localizacaoService.listarTodos();
            return Response.ok(localizacoes).build();
        }
        catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @GET
    @Path("/buscar/{id}")
    public Response buscarLocalizacaoPorId(@PathParam("id") Long id) {
        try{
            Localizacao localizacao = localizacaoService.buscarPorId(id);
            return Response.ok(localizacao).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }

    }

    @POST
    @Path("/criar")
    public Response criarLocalizacao(Localizacao localizacao) {
        try{
            Localizacao nova = localizacaoService.criar(localizacao);
            return Response.status(Response.Status.CREATED).entity(nova).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    public Response atualizarLocalizacao(@PathParam("id") Long id, Localizacao localizacao) {
        try{
            Localizacao atualizada = localizacaoService.atualizar(id, localizacao);
            return Response.ok(atualizada).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("/deletar/{id}")
    public Response deletarLocalizacao(@PathParam("id") Long id) {
        try{
            if (localizacaoService.deletar(id)) {
                return Response.noContent().build();
            }
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Localização com ID " + id + " não encontrada")
                    .build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
}