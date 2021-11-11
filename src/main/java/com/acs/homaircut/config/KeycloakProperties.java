package com.acs.homaircut.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


//@Configuration
//@EnableConfigurationProperties
//@ConfigurationProperties(prefix = Constants.ESSO_KEYCLOAK)
public class KeycloakProperties {
    public final static String serverUrl = "http://localhost:8080/auth";
    public final static String realm = "homaircut-realm";
    public final static String clientId = "homaircut-app";
    public final static String clientSecret = "2a4e9662-baa2-443c-8879-c75ec7ee8d0d";
    public final static String userName = "homaircut-admin";
    public final static String password = "admin";
}
