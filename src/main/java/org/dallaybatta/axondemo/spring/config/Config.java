package org.dallaybatta.axondemo.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "axpn-direct")
public class Config {
    @Getter @Setter private boolean direct;
}
