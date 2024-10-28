package com.henry.online_shopping.config;

import com.henry.online_shopping.utility.BrowserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@DependsOn("setup_cert")
@Profile("https")
public class ProductionEnvStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        final String swaggerUrl = "https://localhost:8443/swagger-ui/index.html";
        final String hstsUrl = BrowserUtils.getDefaultBrowser() + "://net-internals/#hsts";

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c", "start", swaggerUrl);
            ProcessBuilder builder1 = new ProcessBuilder("cmd", "/c", "start", hstsUrl);

            builder.start();
            builder1.start();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        }
    }
}
