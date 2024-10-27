package com.henry.online_shopping.config;

import com.henry.online_shopping.annotation.HttpsOnly;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@HttpsOnly
@Configuration
public class TomcatConfig {

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return server -> {
            Connector httpConnector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
            httpConnector.setPort(8080);
            httpConnector.setSecure(false);
            httpConnector.setScheme("http");

            server.addAdditionalTomcatConnectors(httpConnector);
        };
    }
}
