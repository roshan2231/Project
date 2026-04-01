package election.votepoll.Model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthRequest {
    @NotNull
    private String identifier;

    @NotNull
    private String password;
}
