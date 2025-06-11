package br.com.safezone.resource;

import br.com.safezone.dto.DenunciaDTO;
import br.com.safezone.dto.AtualizaDenunciaDTO;
import br.com.safezone.dto.DenunciaResponseDTO;
import br.com.safezone.exception.ApiException;
import br.com.safezone.model.Denuncia;
import br.com.safezone.security.CurrentUser;
import br.com.safezone.service.DenunciaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/denuncia")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DenunciaResource {

    @Inject
    DenunciaService service;

    @Inject
    CurrentUser currentUser;

    /**
     * Abre uma nova denúncia para o usuário autenticado
     */
    @POST
    @RolesAllowed("cidadao")
    @Path("/criar")
    public Response criar(DenunciaDTO dto) {
        try {
            // obtém usuário logado
            var usuario = currentUser.get();
            if (usuario == null) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            Denuncia criada = service.criarDenunciaCompleta(dto, usuario);
            return Response.status(Response.Status.CREATED)
                    .entity(criada)
                    .build();
        }catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Consulta denúncias detalhadas do usuário autenticado
     */
    @GET
    @Path("/me/detalhes")
    @RolesAllowed({"cidadao","funcionario","admin"})
    public List<DenunciaResponseDTO> listarPorUsuarioDetalhado() {
        try {
            var usuario = currentUser.get();
            return service.listarPorUsuarioComDetalhes(usuario.id);
        }
        catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Atualiza status de uma denúncia (gestor)
     */
    @PATCH
    @Path("/atualiza/{id}")
    @RolesAllowed("funcionario")
    public Response atualizarStatus(@PathParam("id") Long id, AtualizaDenunciaDTO dto) {
        try {
            Denuncia atualizada = service.atualizarStatus(
                    id,
                    dto.status,
                    dto.observacaoResponsavel
            );
            return Response.ok(atualizada).build();
        }
            catch (Exception e) {
            throw new ApiException(e.getMessage(), Response.Status.BAD_REQUEST);
        }
    }
}
