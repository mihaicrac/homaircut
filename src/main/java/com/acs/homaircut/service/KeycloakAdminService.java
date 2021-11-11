package com.acs.homaircut.service;

import com.acs.homaircut.config.KeycloakProperties;
import com.acs.homaircut.dto.RegisterUserDto;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Collections;

@AllArgsConstructor
@Service
public class KeycloakAdminService {
    private final Keycloak keycloakClient;

    public void registerUser(RegisterUserDto registerUserDto) {
        UsersResource userApi = keycloakClient.realms().realm(KeycloakProperties.realm).users();
        Response response;
        try {
            response = userApi.create(toUserRepresentation(registerUserDto));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            response = (((WebApplicationException) ex.getCause()).getResponse());
        }
        checkResponse(response);
    }

    private void checkResponse(Response response) {
        if (response.getStatus() == 201) {
            return;
        }
        JsonNode errorMessage = response.readEntity(JsonNode.class).get("errorMessage");
        throw new RuntimeException(errorMessage.asText());
    }

    private UserRepresentation toUserRepresentation(RegisterUserDto registerUserDto) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEmail(registerUserDto.getEmail());
        userRepresentation.setFirstName(registerUserDto.getFirstName());
        userRepresentation.setLastName(registerUserDto.getLastName());
        userRepresentation.setUsername(registerUserDto.getUsername());
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(registerUserDto.getPassword());
        userRepresentation.setCredentials(Collections.singletonList(credentialRepresentation));
        userRepresentation.setEnabled(true);
        return userRepresentation;
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

}
