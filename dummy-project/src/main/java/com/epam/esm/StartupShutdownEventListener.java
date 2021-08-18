package com.epam.esm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupShutdownEventListener {

    @EventListener
    void onStartup(ApplicationReadyEvent event) {
        log.warn(event.getApplicationContext().toString());
        log.warn(event.getSpringApplication().toString());
    }

    @EventListener
    void onShutdown(ContextStoppedEvent event) {
        // do sth
    }
}