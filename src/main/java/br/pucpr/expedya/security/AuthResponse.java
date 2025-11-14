package br.pucpr.expedya.security;

import java.util.Date;

public class AuthResponse {
    private String email;
    private String token;
    private Date expires;

    // --- ADICIONE OS CONSTRUTORES ---

    // Construtor padrão (bom para serialização)
    public AuthResponse() {
    }

    // Construtor completo que usaremos no controller
    public AuthResponse(String email, String token, Date expires) {
        this.email = email;
        this.token = token;
        this.expires = expires;
    }

    // --- Getters e Setters (o que você já tem) ---

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }
}