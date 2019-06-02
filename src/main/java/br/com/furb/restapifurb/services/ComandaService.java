package br.com.furb.restapifurb.services;

import br.com.furb.restapifurb.common.DeleteResponse;
import br.com.furb.restapifurb.common.Spring;
import br.com.furb.restapifurb.model.comanda.Comanda;
import br.com.furb.restapifurb.repositories.ComandaRepository;
import br.com.furb.restapifurb.repositories.UsuarioRepository;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ComandaService {

    private static final Logger LOGGER = Logger.getLogger(ComandaService.class);

    public List<Comanda> getAll() {
        LOGGER.debug("getAll()");
        var comandas = new ArrayList<Comanda>();
        Optional.ofNullable(Spring.bean(ComandaRepository.class).findAll())
                .ifPresent(result -> result.forEach(comandas::add));
        return comandas;
    }

    public Comanda insert(UUID idUsuario, String produtos, int valorTotal) {
        LOGGER.debug("insert(" + idUsuario + ", " + produtos + ", " + valorTotal + ")");
        var comanda = new Comanda();
        var usuario = Spring.bean(UsuarioRepository.class).findById(idUsuario).get();
        comanda.setUsuario(usuario);
        comanda.setProdutos(produtos);
        comanda.setValorTotal(valorTotal);
        Spring.bean(ComandaRepository.class).save(comanda);
        LOGGER.info(comanda + " cadastrado!");
        return comanda;
    }

    public String deleteById(UUID id) {
        LOGGER.debug("deleteById(" + id + ")");
        Spring.bean(ComandaRepository.class).deleteById(id);
        var out = new DeleteResponse();
        out.setText(id + " foi excluido com sucesso!");
        LOGGER.info(id + " excluido!");
        return out.toString();
    }

    public Comanda put(UUID id, UUID idUsuario, String produtos, int valorTotal) {
        LOGGER.debug("put(" + id + ", " + idUsuario + ", " + produtos + ", " + valorTotal + ")");
        AtomicReference<Comanda> newComanda = new AtomicReference<>(null);
        Spring.bean(ComandaRepository.class).findById(id).ifPresent(comanda -> {
            var usuario = Spring.bean(UsuarioRepository.class).findById(idUsuario).get();
            comanda.setUsuario(usuario);
            comanda.setProdutos(produtos);
            comanda.setValorTotal(valorTotal);
            newComanda.set(Spring.bean(ComandaRepository.class).save(comanda));
        });
        LOGGER.info(newComanda + " teve seus dados alterados!");
        return newComanda.get();
    }

    public Comanda getById(UUID id) {
        LOGGER.debug("getById(" + id + ")");
        var comanda = Spring.bean(ComandaRepository.class).findById(id);
        if (comanda.isPresent())
            return comanda.get();
        else
            return null;
    }

}
