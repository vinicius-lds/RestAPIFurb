package br.com.furb.restapifurb.common;

import org.springframework.context.ConfigurableApplicationContext;

public class Spring {

    private static ConfigurableApplicationContext context = null;

    private Spring() {
    }

    public static <T> T bean(Class<T> bean) {
        if (context == null)
            throw new IllegalStateException("No application context found!");
        return context.getBean(bean);
    }

    public static void setContext(ConfigurableApplicationContext applicationContext) {
        context = applicationContext;
    }
}
