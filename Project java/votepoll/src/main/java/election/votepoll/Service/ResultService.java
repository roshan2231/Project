package election.votepoll.Service;

import election.votepoll.Model.Candidate;
import election.votepoll.Model.Dto.VoteResultDto;
import election.votepoll.Repository.CandidateRepository;
import election.votepoll.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResultService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public List<VoteResultDto> getResultsByElectionAndPosition(Long electionId, Long positionId) {
        List<Candidate> candidates = candidateRepository.findByElectionIdAndPositionId(electionId, positionId);
        List<VoteResultDto> results = new ArrayList<>();

        for (Candidate c : candidates) {
            long count = voteRepository.countByCandidateId(c.getId());
            results.add(new VoteResultDto(c.getId(), c.getName(), c.getParty(), count));
        }

        return results;
    }

    public long getTotalVotesInElection(Long electionId) {
        return voteRepository.countByElectionId(electionId);
    }
}
