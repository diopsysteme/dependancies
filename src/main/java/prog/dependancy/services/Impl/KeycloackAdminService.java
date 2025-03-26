package prog.dependancy.services.Impl;

import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import prog.dependancy.services.interfaces.KeycloackAdminIService;
import prog.dependancy.web.dto.AssignRoleResponseDTO;
import prog.dependancy.web.dto.KeycloakRequestDto;
import prog.dependancy.web.dto.LoginRequestDto;

import java.util.*;

@Service
public class KeycloackAdminService implements KeycloackAdminIService {
    @Value("${spring.security.oauth2.client.registration.keycloak.realm}")
    private String realm;
    @Value("${spring.security.oauth2.client.registration.keycloak.auth-server-url}")
    private String authServerUrl;
    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;
    Keycloak keycloak;
    public KeycloackAdminService(Keycloak keycloak){
        this.keycloak = keycloak;
    }



    @Override
    public Response createUser(UserRepresentation userRepresentation) {
        try {
            var realmResource = keycloak.realm(realm);
            System.out.println("Successfully connected to Keycloak realm: " + realm);
            if (userRepresentation.getUsername() == null || userRepresentation.getUsername().isEmpty()) {
                throw new IllegalArgumentException("Username is required.");
            }

            if (userRepresentation.getCredentials() == null || userRepresentation.getCredentials().isEmpty()) {
                throw new IllegalArgumentException("Password is required.");
            }
            userRepresentation.setEnabled(true);
            Response res = realmResource.users().create(userRepresentation);
            return handleResponse(res);
        } catch (Exception e) {
            System.err.println("Exception during user creation: " + e.getMessage());
            throw new RuntimeException("Failed to create user in Keycloak. Reason: " + e.getMessage(), e);
        }
    }
// DONE
    private Response handleResponse(Response res) {
        if (res.getStatus() >= HttpStatus.OK.value() && res.getStatus() < HttpStatus.MULTIPLE_CHOICES.value()) {
            System.out.println("User successfully created. Status: " + res.getStatus());
            return res;
        } else {
            String errorMessage = res.readEntity(String.class);
            System.err.println("Failed to create user. Status: " + res.getStatus() + ", Message: " + errorMessage);
            throw new RuntimeException("Error from Keycloak: " + errorMessage);
        }
    }
    public Response login(LoginRequestDto loginRequestDto){
        try {
                Keycloak keycloak = keycloackCredentials(loginRequestDto);
                System.out.println("Successfully connected to Keycloak realm: " + keycloak.toString()   );
                AccessTokenResponse token = keycloackCredentials(loginRequestDto).tokenManager().getAccessToken();

            if (token != null) {
                System.out.println("Successfully logged in. Token: " + token);
                return Response.ok().entity(token).build();
            } else {
                System.err.println("Failed to log in. Token is null.");
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            System.err.println("Exception during login: " + e.getMessage());
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
    private Keycloak keycloackCredentials(LoginRequestDto loginRequestDto){
        return KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm(realm)
                .clientId(clientId)
                .grantType(OAuth2Constants.PASSWORD)
                .username(loginRequestDto.getLogin())
                .password(loginRequestDto.getPassword())
                .build();

    }
    @Override
    public Response updateUser(Long id, KeycloakRequestDto keycloakRequestDto) {
        return null;
    }

    @Override
    public Response deleteUser(Long id) {
        try {
            keycloak.realm(realm).users().get(id.toString()).remove();
            return Response.ok().entity("suppression reussie").build();
        } catch (Exception e) {
            System.err.println("Exception during user deletion: " + e.getMessage());
            return Response.status(HttpStatus.NOT_FOUND.value()).entity("erreur lors de la suppression pei=ut etre que le user n'existe pas ").build();
        }

    }
    @Override
    public UserRepresentation getUserById(Long id) {
        return keycloak.realm(realm).users().get(id.toString()).toRepresentation();
    }
    @Override
    public Response getUsers() {
   List<UserRepresentation> users= keycloak.realm(realm).users().list();
        return Response.ok().entity(users).build();
    }
    @Override
    public Response getRoles() {
        List<RoleRepresentation> roles = keycloak.realm(realm).roles().list();
        return Response.ok().entity(roles).build();
    }
    public Optional<UserRepresentation> getUserByUsername(String username){
        return keycloak.realm(realm).users().list().stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
public Optional<RoleRepresentation> getRoleByName(String name){
        return keycloak.realm(realm).roles().list().stream().filter(role -> role.getName().equals(name)).findFirst();}
    @Override
    public Response getRole(String id) {
        RoleResource role = keycloak.realm(realm).roles().get(id);
        if(role != null){
            role.toRepresentation();
            return Response.ok().entity(role).build();
        }else{

         return    null;
        }
    }
    @Override
    public Response createRole(String name) {
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(name);
         keycloak.realm(realm).roles().create(roleRepresentation);
        return getResponse(name,"role created succesffully");
    }
    @Override
    public Response updateRole(String id, String name) {
        RoleRepresentation roleRepresentation = new RoleRepresentation();
        roleRepresentation.setName(name);
        keycloak.realm(realm).roles().get(id).update(roleRepresentation);
        return getResponse(name,"role mofified successfully");
    }

    private Response getResponse(String name,String message){
        Assert.notNull(keycloak.realm(realm).roles().get(name), "Role not found");
        Response response= keycloak.realm(realm).roles().get(name).toRepresentation() != null ? Response.ok().entity(message).build() : Response.status(HttpStatus.NOT_FOUND.value()).entity("erreur lors de la creation du role").build();
        return response;
    }

    public void asignRoleToUser(String userId, List<String> roles){
    Optional<UserRepresentation> user = getUserByUsername(userId);
    roles.forEach(roleName -> {
    Optional<RoleRepresentation> role = getRoleByName(roleName);
        if (user.isPresent() && role.isPresent()) {
            keycloak.realm(realm).users().get(user.get().getId()).roles().realmLevel().add(List.of(role.get()));

        }
    });

}
    public AssignRoleResponseDTO asignRoleToUser(List<String> users, List<String> roles) {
        AssignRoleResponseDTO response = new AssignRoleResponseDTO();
        List<String> failedUsers = new ArrayList<>();
        List<String> failedRoles = new ArrayList<>();

        Map<String, RoleRepresentation> roleMap = new HashMap<>();
        roles.forEach(role -> {
            Optional<RoleRepresentation> roleRepresentation = getRoleByName(role);
            if (roleRepresentation.isPresent()) {
                roleMap.put(role, roleRepresentation.get());
            } else {
                failedRoles.add(role);
            }
        });


        users.forEach(user -> {
            Optional<UserRepresentation> userRepresentation = getUserByUsername(user);
            if (userRepresentation.isPresent()) {
                keycloak.realm(realm).users().get(userRepresentation.get().getId()).roles().realmLevel().add(new ArrayList<>(roleMap.values()));
            } else {
                failedUsers.add(user);
            }
        });
        response.setFailedRoles(failedRoles);

        response.setFailedUsers(failedUsers);
        return response;
    }
    @Override
    public Response deleteRole(String id) {
        keycloak.realm(realm).roles().deleteRole(id);
        return Response.ok().entity("role supprimé").build();
    }

    @Override
    public Response getRoleUsers(String id) {

      Set<UserRepresentation> userRepresentation= keycloak.realm(realm).roles().get(id).getRoleUserMembers();
        return Response.ok().entity(userRepresentation).build();
    }
    @Override
    public Response getRoleUser(String id, String userId) {
        return null;
    }
    private static CredentialRepresentation credentialRepresentation(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }
}

