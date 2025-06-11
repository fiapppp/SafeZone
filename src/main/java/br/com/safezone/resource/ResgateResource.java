package br.com.safezone.resource;

import br.com.safezone.dto.ResgateRequestDTO;
import br.com.safezone.dto.ResgateResponseDTO;
import br.com.safezone.exception.ApiException;
import br.com.safezone.model.ResgateDoacao;
import br.com.safezone.security.CurrentUser;
import br.com.safezone.service.ResgateService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.CREATED;

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
        try{
            var usuario = currentUser.get();
            ResgateDoacao resgate = resgateService.resgatarPontos(usuario, dto.idDoacao);
            ResgateResponseDTO resp = new ResgateResponseDTO();
            resp.id = resgate.id;
            resp.dataResgate = resgate.dataResgate;
            resp.status = resgate.status;
            resp.idUsuario = usuario.id;
            resp.idDoacao = resgate.doacao.id;
            return Response.status(CREATED).entity(resp).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    @POST
    @Path("/conversao/{doacaoId}")
    @RolesAllowed("cidadao")
    public Response resgatarConversao(@PathParam("doacaoId") Long recompensaId, @QueryParam("unidades") @DefaultValue("1") int unidades) {
        try{
            var usuario = currentUser.get();
            ResgateDoacao resgate = resgateService.resgatarConversao(usuario, recompensaId, unidades);
            ResgateResponseDTO resp = new ResgateResponseDTO();
            resp.id = resgate.id;
            resp.dataResgate = resgate.dataResgate;
            resp.status = resgate.status;
            resp.idUsuario = usuario.id;
            resp.idDoacao = resgate.doacao.id;
            return Response.status(CREATED).entity(resp).build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
}