package br.com.furb.restapifurb.controllers;

import br.com.furb.restapifurb.Spring;
import br.com.furb.restapifurb.model.usuario.Usuario;
import br.com.furb.restapifurb.model.usuario.UsuarioDTO;
import br.com.furb.restapifurb.services.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Log4j
@RestController
@RequestMapping(path = "/usuarios")
@Api(value = "Api de usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

    private static final Logger LOGGER = Logger.getLogger(UsuarioController.class);

    /**
     * Busca todos os usuarios cadastrados
     *
     * @return todos os usuarios cadastrados
     */
    @GetMapping
    @ApiOperation("Busca todos os usuarios cadastrados")
    public List<Usuario> getAll() {
        LOGGER.debug("getAll()");
        return Spring.bean(UsuarioService.class).getAll();
    }

    /**
     * Retorna um usuario com base no id
     *
     * @param id id do usuario
     * @return usuario encontrado
     */
    @GetMapping(value = "/{id}")
    public Usuario getById(@PathVariable UUID id) {
        LOGGER.debug("getById(" + id + ")");
        return Spring.bean(UsuarioService.class).getById(id);
    }

    /**
     * Cria um novo usuario
     *
     * @param usuarioDTO espera e usa somente os atributos email e senha
     * @return usuario criado
     */
    @PostMapping
    public Usuario insert(@RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.debug("insert(" + usuarioDTO + ")");
        return Spring.bean(UsuarioService.class).insert(usuarioDTO.getEmail(), usuarioDTO.getSenha());
    }

    /**
     * Altera os dados de um usuario
     *
     * @param id         id do usuario
     * @param usuarioDTO espera e usa somente os atributos email e senha
     * @return usuario alterado
     */
    @PutMapping(value = "/{id}")
    public Usuario put(@PathVariable UUID id, @RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.debug("put(" + id + ", " + usuarioDTO + ")");
        return Spring.bean(UsuarioService.class).put(id, usuarioDTO.getEmail(), usuarioDTO.getSenha());
    }

    /**
     * Exclui um usuario
     *
     * @param id id do usuario
     * @return texto com resposta da operação
     */
    @DeleteMapping(value = "/{id}")
    public String deleteById(@PathVariable UUID id) {
        LOGGER.debug("deleteById(" + id + ")");
        return Spring.bean(UsuarioService.class).deleteById(id);
    }

    /**
     * Exclui um usuario com base no email passado
     *
     * @param usuarioDTO espera e usa somente o atributo email
     * @return texto com resposta da operação
     */
    @DeleteMapping
    public String deleteByEmail(@RequestBody UsuarioDTO usuarioDTO) {
        LOGGER.debug("deleteByEmail(" + usuarioDTO + ")");
        return Spring.bean(UsuarioService.class).deleteByEmail(usuarioDTO.getEmail());
    }

}
