package prog.dependancy.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloackConfig {
    @Value("${spring.security.oauth2.client.registration.keycloak.auth-server-url}")
    private String keycloakServerUrl;
    @Value("${spring.security.oauth2.client.registration.keycloak.realm}")
    private String realm;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.registration.keycloak.admin.username}")
    private String adminUsername;
    @Value("${spring.security.oauth2.client.registration.keycloak.admin.password}")
    private String adminPassword;

@Bean
    Keycloak getKeycloak(){
        return KeycloakBuilder.builder()
                .serverUrl(keycloakServerUrl)
                .realm(realm)
                .clientId(clientId)
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

}
