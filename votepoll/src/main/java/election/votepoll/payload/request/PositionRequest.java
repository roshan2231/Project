package election.votepoll.payload.request;

import lombok.Data;

@Data
public class PositionRequest {
    private String name;
    private Long electionId;
    private String constituency;
}
