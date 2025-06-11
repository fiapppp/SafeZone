package br.com.safezone.resource;

import br.com.safezone.dto.DoacaoDTO;
import br.com.safezone.exception.ApiException;
import br.com.safezone.security.CurrentUser;
import br.com.safezone.service.DoacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@Path("/api/doacao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DoacaoResource {

    @Inject
    DoacaoService service;

    @Inject
    CurrentUser currentUser;

    @GET
    @Path("/listarAtivas")
    @RolesAllowed({"cidadao", "funcionario", "admin"})
    public List<DoacaoDTO> listarAtivas() {
        return service.listarAtivas();
    }

    @POST
    @Transactional
    @Path("/criar")
    @RolesAllowed({"funcionario", "admin"})
    public Response criar(DoacaoDTO dto) {
        try {
            var usuario = currentUser.get();
            DoacaoDTO criada = service.criar(dto, usuario);
            return Response.status(CREATED).entity(criada).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @PUT
    @Path("/atualizar/{id}")
    @Transactional
    @RolesAllowed({"funcionario", "admin"})
    public Response atualizar(@PathParam("id") Long id, DoacaoDTO dto) {
        try {
            DoacaoDTO updated = service.atualizar(id, dto);
            return Response.ok(updated).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @DELETE
    @Path("/deletar/{id}")
    @Transactional
    @RolesAllowed({"funcionario", "admin"})
    public Response deletar(@PathParam("id") Long id) {
        try{
            service.excluir(id);
            return Response.status(NO_CONTENT).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Lista doacao com conversão disponível (>0)
     */
    @GET
    @Path("/conversao")
    @RolesAllowed({"cidadao", "funcionario", "admin"})
    public List<DoacaoDTO> listarConversao() {
        try{
            return service.listarConversao();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
}