package com.visualiser.pathfinder.config;

import com.visualiser.pathfinder.endpoint.AStarService;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(AStarService.class);
    }
}