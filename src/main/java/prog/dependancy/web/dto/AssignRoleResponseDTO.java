package prog.dependancy.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class AssignRoleResponseDTO {
    private List<String> failedRoles;
    private List<String> failedUsers;

}
