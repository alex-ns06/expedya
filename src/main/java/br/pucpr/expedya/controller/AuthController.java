package br.pucpr.expedya.controller;

import br.pucpr.expedya.dto.AuthRequest;
import br.pucpr.expedya.dto.AuthResponse;
import br.pucpr.expedya.dto.ClienteDTO;
import br.pucpr.expedya.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final ClienteController clienteController;

    public AuthController(JwtUtil jwtUtil, ClienteController clienteController) {
        this.jwtUtil = jwtUtil;
        this.clienteController = clienteController;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        List<ClienteDTO> clientes = clienteController.findAll();

        for (ClienteDTO c : clientes) {
            if (c.getEmail().equals(request.getEmail()) && c.getSenha().equals(request.getSenha())) {
                String token = jwtUtil.generateToken(c.getEmail(), c.getRole());
                return ResponseEntity.ok(new AuthResponse(token));
            }
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }
}
