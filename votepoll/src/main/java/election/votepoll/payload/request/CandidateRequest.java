package election.votepoll.payload.request;

import lombok.Data;

@Data
public class CandidateRequest {
    private Long electionId;
    private Long positionId;
    private String name;
    private String party;
    private String symbol;
    private String details;
}
