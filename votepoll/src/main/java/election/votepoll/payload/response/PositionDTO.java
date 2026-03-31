package election.votepoll.payload.response;

import lombok.Data;

@Data
public class PositionDTO {
    private Long id;
    private String name;
    private String constituency;
    private Long electionId;
}
