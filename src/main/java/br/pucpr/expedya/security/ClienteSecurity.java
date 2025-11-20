package br.pucpr.expedya.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("clienteSecurity")
public class ClienteSecurity {

    /**
     * Verifica se o usuário autenticado é o dono do recurso.
     */
    public boolean checkId(Authentication authentication, Long id) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof ClienteAuthentication clienteAuth)) {
            return false;
        }

        Long usuarioId = clienteAuth.getId() != null
                ? clienteAuth.getId().longValue()
                : null;

        if (usuarioId == null) {
            return false;
        }

        return usuarioId.equals(id);
    }
}
