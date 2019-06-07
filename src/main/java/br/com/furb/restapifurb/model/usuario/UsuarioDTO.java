package br.com.furb.restapifurb.model.usuario;

import java.util.UUID;

public class UsuarioDTO {

    private UUID id;
    private String email;
    private String senha;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("E-mail inválido");
        }
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        if(senha == null || senha.isEmpty()){
            throw new IllegalArgumentException("Senha do e-mail inválida");
        }
        this.senha = senha;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                '}';
    }
}
