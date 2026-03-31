package election.votepoll.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank
    private String voterID;

    @NotBlank
    private String password;
}
