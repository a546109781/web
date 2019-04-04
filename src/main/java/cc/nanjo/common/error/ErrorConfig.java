package cc.nanjo.common.error;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @Author: xw
 * 2019-3-29 11:04
 */
@Configuration
public class ErrorConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            ErrorPage errorPage400 = new ErrorPage(HttpStatus.BAD_REQUEST, "/error-400");
            ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-404");
            ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-500");
            factory.addErrorPages(errorPage400, errorPage404, errorPage500);
        };
    }

}
