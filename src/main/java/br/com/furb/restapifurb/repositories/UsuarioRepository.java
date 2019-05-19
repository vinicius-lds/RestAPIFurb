package br.com.furb.restapifurb.repositories;

import br.com.furb.restapifurb.model.usuario.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, UUID> {

    Optional<List<Usuario>> findAllByEmail(String email);

}
