package election.votepoll.Model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PositionDto {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long electionId;

    @NotNull
    private String constituency;
}
