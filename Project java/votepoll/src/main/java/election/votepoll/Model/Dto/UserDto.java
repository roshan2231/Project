package election.votepoll.Model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String voterId;

    private String email;
    private String password;

    @NotNull
    private String constituency;

    @NotNull
    private String role;
}
