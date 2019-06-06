package br.com.furb.restapifurb.common.security;

import br.com.furb.restapifurb.common.Spring;
import br.com.furb.restapifurb.model.usuario.Usuario;
import br.com.furb.restapifurb.model.usuario.UsuarioDTO;
import br.com.furb.restapifurb.repositories.UsuarioRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<List<Usuario>> allByEmailOpt = Spring.bean(UsuarioRepository.class).findAllByEmail(email);
        if (allByEmailOpt.isEmpty()) {
            return null;
        } else {
            List<Usuario> usuarios = allByEmailOpt.get();
            if (usuarios.stream().findFirst().isEmpty()) {
                return null;
            } else {
                return new JwtUsuario(usuarios.stream().findFirst().get());
            }
        }
    }

    private class JwtUsuario extends UsuarioDTO implements UserDetails {

        private Collection<? extends GrantedAuthority> authoroties = Arrays.asList(new Authority("ADMIN"), new Authority("USER"));

        private JwtUsuario(Usuario usuario) {
            super();
            super.setId(usuario.getId());
            super.setEmail(usuario.getEmail());
            super.setSenha(usuario.getSenha());
        }

        @JsonIgnore
        public UUID getId() {
            return super.getId();
        }

        @Override
        public String getUsername() {
            return super.getEmail();
        }

        @JsonIgnore
        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @JsonIgnore
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @JsonIgnore
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @JsonIgnore
        @Override
        public String getPassword() {
            return super.getSenha();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.authoroties;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

    }

    private class Authority implements GrantedAuthority {
        private String authority;

        public Authority(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return this.authority;
        }
    }
}
