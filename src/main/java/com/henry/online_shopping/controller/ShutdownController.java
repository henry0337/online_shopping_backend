package com.henry.online_shopping.controller;

import com.henry.online_shopping.annotation.OneLineMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {

    /**
     * Enables an utility <b>Shutdown URL</b> for the server. <br>
     * (Inspired by <a href="https://ktor.io/">Ktor</a> project.)
     */
    @OneLineMapping(
            method = RequestMethod.POST,
            url = "/api/shutdown",
            name = "Shutdown URL",
            description = "Can be used to safely shutdown the application.")
    public final void shutdown() {
        // This route will always return HTTP response as "200 OK" by default.
        System.exit(0);
    }
}
