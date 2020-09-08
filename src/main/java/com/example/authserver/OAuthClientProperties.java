package com.example.authserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "oauth")
@Data
public class OAuthClientProperties {
    private List<AppClient> appClients;
}
