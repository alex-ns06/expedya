package br.pucpr.expedya.security;

import br.pucpr.expedya.model.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest request) {
        var cliente = new Cliente();

        UserAuthentication userAuthentication = new UserAuthentication();
        userAuthentication.setEmail(request.getEmail());

        String jwtToken = jwtService.generateToken(userAuthentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtToken);
        authResponse.setEmail(user.getEmail());
        authResponse.setExpires(new Date(System.currentTimeMillis() + 1000 * 60 * 24));

        return authResponse;
    }
}
