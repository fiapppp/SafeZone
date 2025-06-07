package br.com.safezone.resource;

import br.com.safezone.security.CurrentUser;
import br.com.safezone.service.PontuacaoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/pontuacao")
@Produces(MediaType.APPLICATION_JSON)
public class PontuacaoResource {

    @Inject
    PontuacaoService service;

    @Inject
    CurrentUser currentUser;

    @GET
    @Path("/me/total")
    @RolesAllowed({"cidadao","funcionario","admin"})
    public Response consultarMeuTotal() {
        var usuario = currentUser.get();
        int total = service.getPontuacaoTotal(usuario);
        return Response.ok(total).build();
    }
}
