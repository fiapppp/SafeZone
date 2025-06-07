package br.com.safezone.security;

import br.com.safezone.model.Sessao;
import br.com.safezone.model.Usuario;
import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;

@Provider
@ApplicationScoped
@Priority(Priorities.AUTHENTICATION)
public class TokenAuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authHeader = requestContext.getHeaderString("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Sessao s = Sessao.find("token", token).firstResult();
            if (s != null && s.dataExpiracao.isAfter(LocalDateTime.now())) {
                Usuario u = s.usuario;
                Set<String> roles = Set.of(u.perfil.nomePerfil.toLowerCase());
                SecurityContext sc = new SecurityContext() {
                    @Override
                    public Principal getUserPrincipal() {
                        return () -> u.email;
                    }
                    @Override public boolean isUserInRole(String role) { return roles.contains(role); }
                    @Override public boolean isSecure() { return requestContext.getSecurityContext().isSecure(); }
                    @Override public String getAuthenticationScheme() { return "Bearer"; }
                };
                requestContext.setSecurityContext(sc);
                return;
            }
        }
        // não autenticado: prossegue para @RolesAllowed falhar ou retorna 401
    }
}
