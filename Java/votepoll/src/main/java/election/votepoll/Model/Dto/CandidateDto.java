package election.votepoll.Model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CandidateDto {
    private Long id;

    @NotNull
    private Long electionId;

    @NotNull
    private String name;

    private String party;

    @NotNull
    private Long positionId;

    private String symbol;
    private String details;
}
