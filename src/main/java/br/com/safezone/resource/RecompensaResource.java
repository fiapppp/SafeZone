package br.com.safezone.resource;

import br.com.safezone.dto.RecompensaDTO;
import br.com.safezone.security.CurrentUser;
import br.com.safezone.service.RecompensaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import static jakarta.ws.rs.core.Response.Status.CREATED;
import static jakarta.ws.rs.core.Response.Status.NO_CONTENT;

@Path("/api/recompensa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecompensaResource {

    @Inject
    RecompensaService service;

    @Inject
    CurrentUser currentUser;

    @GET
    @Path("/listarAtivas")
    @RolesAllowed({"cidadao", "funcionario", "admin"})
    public List<RecompensaDTO> listarAtivas() {
        return service.listarAtivas();
    }

    @POST
    @Transactional
    @Path("/criar")
    @RolesAllowed({"funcionario", "admin"})
    public Response criar(RecompensaDTO dto) {
        var usuario = currentUser.get();
        RecompensaDTO criada = service.criar(dto, usuario);
        return Response.status(CREATED).entity(criada).build();
    }

    @PUT
    @Path("/atualizar/{id}")
    @Transactional
    @RolesAllowed({"funcionario", "admin"})
    public Response atualizar(@PathParam("id") Long id, RecompensaDTO dto) {
        RecompensaDTO updated = service.atualizar(id, dto);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/deletar/{id}")
    @Transactional
    @RolesAllowed({"funcionario", "admin"})
    public Response deletar(@PathParam("id") Long id) {
        service.excluir(id);
        return Response.status(NO_CONTENT).build();
    }
}