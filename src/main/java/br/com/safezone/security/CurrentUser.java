package br.com.safezone.security;

import br.com.safezone.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class CurrentUser {

    @Inject
    SecurityContext securityContext;

    /**
     * Retorna o usuário autenticado, obtendo o login (email) do SecurityContext
     */
    public Usuario get() {
        if (securityContext.getUserPrincipal() == null) {
            return null;
        }
        String email = securityContext.getUserPrincipal().getName();
        return Usuario.find("email", email).firstResult();
    }
}