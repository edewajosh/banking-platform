package com.bank.acccounts.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bank.platform.customer")
public record CustomerConfigs(String baseUrl) {
}
