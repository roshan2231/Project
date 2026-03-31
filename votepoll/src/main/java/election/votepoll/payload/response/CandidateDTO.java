package election.votepoll.payload.response;

import lombok.Data;

@Data
public class CandidateDTO {
    private Long id;
    private String name;
    private String party;
    private String symbol;
    private String details;
    private Long electionId;
    private Long positionId;
}
