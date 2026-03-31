package election.votepoll.payload.response;

import election.votepoll.models.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String voterID;
    private String email;
    private String constituency;
    private Role role;
}
