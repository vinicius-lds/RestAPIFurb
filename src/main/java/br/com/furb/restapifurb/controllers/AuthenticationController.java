package br.com.furb.restapifurb.controllers;

import br.com.furb.restapifurb.common.JwtTokenUtil;
import br.com.furb.restapifurb.common.Spring;
import br.com.furb.restapifurb.common.WebSecurityConfig;
import br.com.furb.restapifurb.model.usuario.UsuarioDTO;
import br.com.furb.restapifurb.repositories.UsuarioRepository;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Log4j
@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private static final Logger LOGGER = Logger.getLogger(ComandaController.class);
    private String tokenHeader = "Authorization";
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @PostMapping(path = "/login")
    public ResponseEntity<?> createAuthorizationToken(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
        Authentication authentication = Spring.bean(WebSecurityConfig.class)
                .authenticationManagerBean()
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                usuarioDTO.getEmail(),
                                usuarioDTO.getSenha()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        try {
            Spring.bean(UsuarioRepository.class).findAllByEmail(usuarioDTO.getEmail()).get().stream().findFirst().get();
            var token = Spring.bean(JwtTokenUtil.class).generateToken(usuarioDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();

        }
        /*var usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setSenha(usuario.getSenha());*/


    }

//    @RequestMapping(value = "/auth", method = RequestMethod.POST)
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequestDTO authenticationRequest) {
//
//        authenticate(authenticationRequest.getEmail(), authenticationRequest.getSenha());
//
//        // Reload password post-security so we can generate the token
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
//        final String token = jwtTokenUtil.generateToken((UsuarioDTO) userDetails);
//
//        // Return the token
//        return ResponseEntity.ok(token);
//    }

    @RequestMapping(value = "/auth/refesh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getEmailFromToken(token);
        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (user.isAccountNonExpired()) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(refreshedToken);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Authenticates the user. If something is wrong, an {@link IllegalArgumentException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new IllegalArgumentException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new IllegalArgumentException("Bad credentials!", e);
        }
    }
}
