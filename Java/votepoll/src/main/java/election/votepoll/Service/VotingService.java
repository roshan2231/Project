package election.votepoll.Service;

import election.votepoll.Model.Dto.VoteDto;

public interface VotingService {

     VoteDto castVote(VoteDto dto);
}
