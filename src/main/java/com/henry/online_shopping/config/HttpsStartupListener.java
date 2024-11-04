package com.henry.online_shopping.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("https")
public class HttpsStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        try {
            final String swaggerUrl = "https://localhost:8443/swagger-ui/index.html";
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "start", swaggerUrl);
            builder.start();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
