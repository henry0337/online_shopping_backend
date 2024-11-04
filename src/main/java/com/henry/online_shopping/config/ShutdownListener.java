package com.henry.online_shopping.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ShutdownListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(@NonNull ContextClosedEvent event) {
        // Make sure any JVM instances or service (like OpenJDK Platform Binary you may see in Task Manager)
        // that be used to load project will shutdown in any circumstances
        System.exit(0);
    }
}
