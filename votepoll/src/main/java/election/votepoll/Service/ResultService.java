package election.votepoll.Service;

import election.votepoll.Model.Candidate;
import election.votepoll.Model.Dto.VoteResultDto;

import java.util.List;

public interface ResultService {

    List<VoteResultDto> getResultsByElectionAndPosition(Long electionId, Long positionId);
    List<VoteResultDto> getResultsByElection(Long electionId);

    long getTotalVotesInElection(Long electionId);

    List<VoteResultDto> determineWinner(Long electionId);
}
