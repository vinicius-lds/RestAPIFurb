package br.com.furb.restapifurb.services;

import br.com.furb.restapifurb.Spring;
import br.com.furb.restapifurb.controllers.response.DeleteResponse;
import br.com.furb.restapifurb.model.usuario.Usuario;
import br.com.furb.restapifurb.repositories.UsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class);

    public List<Usuario> getAll() {
        LOGGER.debug("getAll()");
        ArrayList<Usuario> usuarios = new ArrayList<>();
        Optional.ofNullable(Spring.bean(UsuarioRepository.class).findAll())
                .ifPresent(result -> result.forEach(usuarios::add));
        return usuarios;
    }

    public Usuario insert(String email, String senha) {
        LOGGER.debug("insert(" + email + ", " + senha + ")");
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        Spring.bean(UsuarioRepository.class).save(usuario);
        LOGGER.info(usuario + " cadastrado!");
        return usuario;
    }

    public String deleteById(UUID id) {
        LOGGER.debug("deleteById(" + id + ")");
        Spring.bean(UsuarioRepository.class).deleteById(id);
        DeleteResponse out = new DeleteResponse();
        out.setText(id + " foi excluido com sucesso!");
        LOGGER.info(id + " excluido!");
        return out.toString();
    }

    public Usuario put(UUID id, String email, String senha) {
        LOGGER.debug("put(" + id + ", " + email + ", " + senha + ")");
        AtomicReference<Usuario> newUsuario = new AtomicReference<>(null);
        Spring.bean(UsuarioRepository.class).findById(id).ifPresent(usuario -> {
            usuario.setEmail(email);
            usuario.setSenha(senha);
            newUsuario.set(Spring.bean(UsuarioRepository.class).save(usuario));
        });
        LOGGER.info(newUsuario + " teve seus dados alterados!");
        return newUsuario.get();
    }

    public String deleteByEmail(String email) {
        LOGGER.debug("deleteByEmail(" + email + ")");
        AtomicReference<List<UUID>> ids = new AtomicReference<>(Collections.emptyList());
        Spring.bean(UsuarioRepository.class)
                .findAllByEmail(email)
                .ifPresent(usuarios ->
                        ids.set(usuarios.stream().map(Usuario::getId).collect(Collectors.toList()))
                );
        DeleteResponse out = new DeleteResponse();
        if (ids.get().isEmpty()) {
            out.setText("Email '" + email + "' não foi encontrado!");
        } else {
            ids.get().forEach(Spring.bean(UsuarioRepository.class)::deleteById);
            StringJoiner joiner = new StringJoiner(", ");
            ids.get().forEach(id -> joiner.add(id.toString()));
            String logResponse = joiner.toString() + " deletados com sucesso!";
            out.setText(logResponse);
            LOGGER.info(logResponse);
        }
        return out.toString();
    }

    public Usuario getById(UUID id) {
        LOGGER.debug("getById(" + id + ")");
        Optional<Usuario> usuario = Spring.bean(UsuarioRepository.class).findById(id);
        if (usuario.isPresent())
            return usuario.get();
        else
            return null;
    }
}
