package election.votepoll.Model.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class VoteDto {
    private Long id;

    @NotNull
    private String voterId;

    @NotNull
    private Long userId;

    @NotNull
    private Long candidateId;

    @NotNull
    private Long electionId;

    @NotNull
    private Long positionId;

    private LocalDateTime timestamp;
}
