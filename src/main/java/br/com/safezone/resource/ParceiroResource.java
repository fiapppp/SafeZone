package br.com.safezone.resource;

import br.com.safezone.dto.ParceiroDTO;
import br.com.safezone.model.Parceiro;
import br.com.safezone.service.ParceiroService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api/parceiro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParceiroResource {

    @Inject
    ParceiroService service;

    @GET
    @RolesAllowed({"funcionario","admin"})
    @Path("/listar")
    public List<ParceiroDTO> listar() {
        return service.listarTodos().stream().map(p -> {
            ParceiroDTO dto = new ParceiroDTO();
            dto.id = p.id;
            dto.nomeParceiro = p.nomeParceiro;
            dto.contato = p.contato;
            dto.cnpj = p.cnpj;
            dto.status = p.status;
            return dto;
        }).collect(Collectors.toList());
    }

    @GET
    @Path("/buscar/{id}")
    @RolesAllowed({"funcionario","admin"})
    public Response buscar(@PathParam("id") Long id) {
        Parceiro p = service.buscarPorId(id);
        if (p == null) return Response.status(Response.Status.NOT_FOUND).build();
        ParceiroDTO dto = new ParceiroDTO();
        dto.id = p.id; dto.nomeParceiro = p.nomeParceiro;
        dto.contato = p.contato; dto.cnpj = p.cnpj; dto.status = p.status;
        return Response.ok(dto).build();
    }

    @POST
    @Path("/criar")
    @RolesAllowed({"funcionario","admin"})
    public Response criar(ParceiroDTO dto) {
        Parceiro p = new Parceiro();
        p.nomeParceiro = dto.nomeParceiro;
        p.contato = dto.contato;
        p.cnpj = dto.cnpj;
        p.status = dto.status != null ? dto.status : 1;
        service.criar(p);
        dto.id = p.id;
        return Response.status(Response.Status.CREATED).entity(dto).build();
    }

    @PUT
    @Path("/atualizar/{id}")
    @RolesAllowed({"funcionario","admin"})
    public Response atualizar(@PathParam("id") Long id, ParceiroDTO dto) {
        Parceiro dados = new Parceiro();
        dados.nomeParceiro = dto.nomeParceiro;
        dados.contato = dto.contato;
        dados.cnpj = dto.cnpj;
        dados.status = dto.status;
        Parceiro atualizado = service.atualizar(id, dados);
        if (atualizado == null) return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(dto).build();
    }

    @DELETE
    @Path("/excluir/{id}")
    @RolesAllowed({"funcionario","admin"})
    public Response excluir(@PathParam("id") Long id) {
        service.excluir(id);
        return Response.noContent().build();
    }
}