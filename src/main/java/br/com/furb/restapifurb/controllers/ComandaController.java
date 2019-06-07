package br.com.furb.restapifurb.controllers;

import br.com.furb.restapifurb.Spring;
import br.com.furb.restapifurb.model.comanda.Comanda;
import br.com.furb.restapifurb.model.comanda.ComandaDTO;
import br.com.furb.restapifurb.services.ComandaService;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j
@RestController
@RequestMapping(path = "/comandas")
public class ComandaController {

    private static final Logger LOGGER = Logger.getLogger(ComandaController.class);

    /**
     * Busca todos as comandas cadastradas
     *
     * @return todos as comandas cadastradas
     */
    @GetMapping
    public List<Comanda> getAll() {
        LOGGER.debug("getAll()");
        return Spring.bean(ComandaService.class).getAll();
    }

    /**
     * Retorna uma comanda com base no id
     *
     * @param id id da comanda
     * @return comanda encontrada
     */
    @GetMapping(path = "/{id}")
    public Comanda getById(@PathVariable UUID id) {
        LOGGER.debug("getById(" + id + ")");
        return Spring.bean(ComandaService.class).getById(id);
    }

    /**
     * Cria uma nova comanda
     *
     * @param comandaDTO espera e usa somente os atributos idusuario, produtos e valortotal
     * @return comanda criada
     */
    @PostMapping
    public Comanda insert(@RequestBody ComandaDTO comandaDTO) {
        LOGGER.debug("intert(" + comandaDTO + ")");
        return Spring.bean(ComandaService.class).insert(comandaDTO.getIdUsuario(), comandaDTO.getProdutos(), comandaDTO.getValorTotal());
    }

    /**
     * Altera os dados de uma comanda
     *
     * @param id         id da comanda
     * @param comandaDTO espera e usa somente os atributos idusuario, produtos e valortotal
     * @return comanda alterada
     */
    @PutMapping(path = "/{id}")
    public Comanda put(@PathVariable UUID id, @RequestBody ComandaDTO comandaDTO) {
        LOGGER.debug("put(" + id + ", " + comandaDTO + ")");
        return Spring.bean(ComandaService.class).put(id, comandaDTO.getIdUsuario(), comandaDTO.getProdutos(), comandaDTO.getValorTotal());
    }

    /**
     * Exclui uma comanda
     *
     * @param id id da comanda
     * @return texto com resposta da operação
     */
    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable UUID id) {
        LOGGER.debug("deleteById(" + id + ")");
        return Spring.bean(ComandaService.class).deleteById(id);
    }

}
