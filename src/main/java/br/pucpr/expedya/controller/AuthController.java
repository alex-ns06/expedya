package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.AuthRequest;
import br.pucpr.expedya.dto.AuthResponse;
import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final ClienteController clienteController;

    public AuthController(JwtUtil jwtUtil, ClienteController clienteController) {
        this.jwtUtil = jwtUtil;
        this.clienteController = clienteController;
    }

    // Exemplo de método de autenticação no AuthController
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        List<ClienteDTO> clientes = clienteController.findAll();

        // Supondo que você tenha acesso à lista de clientes
        Optional<ClienteDTO> clienteOpt = clientes.stream()
                .filter(c -> c.getEmail().equals(request.getEmail()) && c.getSenha().equals(request.getSenha()))
                .findFirst();

        if (clienteOpt.isPresent()) {
            ClienteDTO cliente = clienteOpt.get();
            String token = jwtUtil.generateToken(cliente.getEmail(), cliente.getRole());
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
