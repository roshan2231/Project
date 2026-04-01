package election.votepoll.Model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import election.votepoll.Model.Enums.Role;
import election.votepoll.Model.Enums.Status;

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
    private Role role;

    private Status status;
}
