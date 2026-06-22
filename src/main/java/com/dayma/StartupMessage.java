package com.dayma;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StartupMessage {

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        System.out.println("██████╗  █████╗ ██╗   ██╗███╗   ███╗ █████╗");
        System.out.println("██╔══██╗██╔══██╗╚██╗ ██╔╝████╗ ████║██╔══██╗");
        System.out.println("██║  ██║███████║ ╚████╔╝ ██╔████╔██║███████║");
        System.out.println("██║  ██║██╔══██║  ╚██╔╝  ██║╚██╔╝██║██╔══██║");
        System.out.println("██████╔╝██║  ██║   ██║   ██║ ╚═╝ ██║██║  ██║");
        System.out.println("╚═════╝ ╚═╝  ╚═╝   ╚═╝   ╚═╝     ╚═╝╚═╝  ╚═╝");
    }
}
