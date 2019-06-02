package br.com.furb.restapifurb.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Teste {

    @GetMapping(path = "/error")
    public String get() {
        return "Error";
    }

}
