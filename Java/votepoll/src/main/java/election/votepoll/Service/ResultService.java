package election.votepoll.Service;

import election.votepoll.Model.Dto.VoteResultDto;

import java.util.List;

public interface ResultService {

    List<VoteResultDto> getResultsByElectionAndPosition(Long electionId, Long positionId);

    long getTotalVotesInElection(Long electionId);
}
