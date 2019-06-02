package br.com.furb.restapifurb.controllers;

import br.com.furb.restapifurb.common.JwtTokenUtil;
import br.com.furb.restapifurb.common.Spring;
import br.com.furb.restapifurb.model.usuario.UsuarioDTO;
import br.com.furb.restapifurb.repositories.UsuarioRepository;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j
@RestController
@RequestMapping(path = "auth")
public class AuthenticationController {

    private static final Logger LOGGER = Logger.getLogger(ComandaController.class);

    @PostMapping
    public ResponseEntity<?> createAuthorizationToke(@RequestBody UsuarioDTO usuarioDTO) {
        var authentication = Spring.bean(AuthenticationManager.class)
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                usuarioDTO.getEmail(),
                                usuarioDTO.getSenha()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var usuario = Spring.bean(UsuarioRepository.class).findAllByEmail(usuarioDTO.getEmail()).get().stream().findFirst().get();
        var token = Spring.bean(JwtTokenUtil.class).generateToken(usuario);
        return ResponseEntity.ok(token);
    }
}
