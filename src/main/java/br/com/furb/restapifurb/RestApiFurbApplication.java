package br.com.furb.restapifurb;

import br.com.furb.restapifurb.common.Spring;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j
@SpringBootApplication
public class RestApiFurbApplication {

    private static final Logger LOGGER = Logger.getLogger(RestApiFurbApplication.class);

    public static void main(String[] args) {
        Spring.setContext(SpringApplication.run(RestApiFurbApplication.class, args));
        LOGGER.info("Aplicação iniciada!");
    }

}
