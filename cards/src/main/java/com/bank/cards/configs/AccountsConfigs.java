package com.bank.cards.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "banking.platform.account")
public record AccountsConfigs (String baseUrl){
}
