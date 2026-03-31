package election.votepoll.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String voterID;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String constituency;
    
    // Optional role for admin creation testing
    private String role;
}
