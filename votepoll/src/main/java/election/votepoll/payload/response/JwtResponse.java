package election.votepoll.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private Long id;
    private String voterID;
    private String name;
    private String email;
    private String role;
}
