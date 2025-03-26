package prog.dependancy.services.interfaces;

import jakarta.ws.rs.core.Response;
import org.keycloak.representations.idm.UserRepresentation;
import prog.dependancy.web.dto.KeycloakRequestDto;
public interface KeycloackAdminIService {

public Response createUser(UserRepresentation crUKcReqDTO);
public Response updateUser(Long id, KeycloakRequestDto keycloakRequestDto);
public Response deleteUser(Long id);
public UserRepresentation getUserById(Long id);
public Response getUsers();
public Response getRoles();
public Response getRole(String id);
public Response createRole(String name);
public Response updateRole(String id, String name);
public Response deleteRole(String id);
public Response getRoleUsers(String id);
public Response getRoleUser(String id, String userId);



}
