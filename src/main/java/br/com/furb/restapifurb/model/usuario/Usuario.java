package br.com.furb.restapifurb.model.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String senha;

    public Usuario() {
    }

    public Usuario(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

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

}
