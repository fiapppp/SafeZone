package br.com.safezone.resource;

import br.com.safezone.dto.LoginRequestDTO;
import br.com.safezone.dto.LoginResponseDTO;
import br.com.safezone.model.Sessao;
import br.com.safezone.model.Usuario;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.UUID;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final int SESSAO_DURA_HORAS = 8;

    @POST
    @Path("/login")
    @Transactional
    public Response login(LoginRequestDTO dto) {
        Usuario u = Usuario.find("email", dto.email).firstResult();
        if (u == null || !u.senha.equals(dto.senha)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        // cria token
        String token = UUID.randomUUID().toString();
        Sessao s = new Sessao();
        s.token = token;
        s.usuario = u;
        s.dataExpiracao = LocalDateTime.now().plusHours(SESSAO_DURA_HORAS);
        s.persist();

        LoginResponseDTO resp = new LoginResponseDTO();
        resp.token = token;
        resp.expiraEm = s.dataExpiracao.atZone(java.time.ZoneId.systemDefault()).toEpochSecond();
        return Response.ok(resp).build();
    }

    @POST
    @Path("/logout")
    @Transactional
    public Response logout(@HeaderParam("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        String token = authHeader.substring(7);
        Sessao.delete("token", token);
        return Response.noContent().build();
    }
}