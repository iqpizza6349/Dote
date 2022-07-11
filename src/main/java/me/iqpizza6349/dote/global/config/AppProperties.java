package me.iqpizza6349.dote.global.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private String secret;
    private String refreshSecret;
    private String clientId;
    private String clientSecret;
}
