package br.com.furb.restapifurb.repositories;

import br.com.furb.restapifurb.model.comanda.Comanda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComandaRepository extends CrudRepository<Comanda, UUID> {
}
