package br.com.safezone.resource;

import br.com.safezone.dto.ResgateRequestDTO;
import br.com.safezone.dto.ResgateResponseDTO;
import br.com.safezone.model.ResgateDoacao;
import br.com.safezone.security.CurrentUser;
import br.com.safezone.service.ResgateService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/resgate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ResgateResource {

    @Inject
    ResgateService resgateService;

    @Inject
    CurrentUser currentUser;

    @POST
    @RolesAllowed("cidadao")
    @Path("/api/resgatar")
    public Response resgatar(ResgateRequestDTO dto) {
        var usuario = currentUser.get();
        ResgateDoacao resgate = resgateService.resgatarPontos(usuario, dto.idRecompensa);
        ResgateResponseDTO resp = new ResgateResponseDTO();
        resp.id = resgate.id;
        resp.dataResgate = resgate.dataResgate;
        resp.status = resgate.status;
        resp.idUsuario = usuario.id;
        resp.idRecompensa = resgate.recompensa.id;
        return Response.status(Response.Status.CREATED).entity(resp).build();
    }
}