package prog.dependancy.web.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRequestDto {
    private String login;
    private String password;
}
