package com.henry.online_shopping.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile({"http"})
public class DevelopmentEnvStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        final String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";
        final String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder builder = null;

        try {
            if (os.contains("win")) {
                builder = new ProcessBuilder("cmd", "/c", "start", swaggerUrl);
            } else if (os.contains("mac")) {
                builder = new ProcessBuilder("open", swaggerUrl);
            } else if (os.contains("nix") || os.contains("nux")) {
                builder = new ProcessBuilder("xdg-open", swaggerUrl);
            }

            assert builder != null;
            builder.start();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
