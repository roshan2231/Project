package election.votepoll.payload.request;

import lombok.Data;

@Data
public class VoteRequest {
    private Long electionId;
    private Long positionId;
    private Long candidateId;
}
