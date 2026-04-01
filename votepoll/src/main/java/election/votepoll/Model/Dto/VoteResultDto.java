package election.votepoll.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteResultDto {
    private Long candidateId;
    private String candidateName;
    private String party;
    private Long voteCount;
}
