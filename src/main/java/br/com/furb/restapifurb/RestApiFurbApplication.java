package br.com.furb.restapifurb;

import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
@Log4j
public class RestApiFurbApplication {

    private final static Logger logger = Logger.getLogger("br.com.furb.restapifurb.RestApiFurbApplication");

    public static void main(String[] args) {
        SpringApplication.run(RestApiFurbApplication.class, args);
        logger.info("Aplicação iniciada");
    }

}
