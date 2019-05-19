package br.com.furb.restapifurb;

import org.springframework.context.ConfigurableApplicationContext;

public class Spring {

    private static ConfigurableApplicationContext context = null;

    private Spring() {
    }

    /**
     * Tem que achar a impl oficial dessa merda
     */
    public static <T> T bean(Class<T> bean) {
        if (context == null)
            return null;
        return context.getBean(bean);
    }

    public static void setContext(ConfigurableApplicationContext applicationContext) {
        context = applicationContext;
    }
}
