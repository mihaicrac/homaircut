package com.acs.homaircut.api;

import com.acs.homaircut.dto.RegisterUserDto;
import com.acs.homaircut.dto.SignInUserDto;
import com.acs.homaircut.service.KeycloakAdminService;
import lombok.AllArgsConstructor;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.authorization.client.Configuration;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import static com.acs.homaircut.config.KeycloakProperties.*;

@AllArgsConstructor
@RestController
public class KeycloakAdminController {
    private final KeycloakAdminService keycloakAdminService;

    @PostMapping("users")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(@RequestBody RegisterUserDto registerUserDto) {
        keycloakAdminService.registerUser(registerUserDto);
    }

    @PostMapping(path = "users/signin")
    public Mono<AccessTokenResponse> signin(@RequestBody SignInUserDto signInUserDto) {
        Map<String, Object> clientCredentials = new HashMap<>();
        clientCredentials.put("secret", clientSecret);
        clientCredentials.put("grant_type", "password");

        Configuration configuration =
                new Configuration(serverUrl, realm, clientId, clientCredentials, null);
        AuthzClient authzClient = AuthzClient.create(configuration);

        AccessTokenResponse response;
        try {
            response =
                    authzClient.obtainAccessToken(signInUserDto.getEmail(), signInUserDto.getPassword());
        } catch (Exception ex) {
            throw new RuntimeException();
        }

        return Mono.just(response);
    }

    @GetMapping(value = "/protected-data")
    public String getEmail(Principal principal) {
        return "Hello, this api is protected.";
    }
}
