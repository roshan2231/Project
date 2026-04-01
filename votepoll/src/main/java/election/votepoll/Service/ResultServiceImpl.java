package election.votepoll.Service;

import election.votepoll.Model.Candidate;
import election.votepoll.Model.Dto.VoteResultDto;
import election.votepoll.Repository.CandidateRepository;
import election.votepoll.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

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

    public List<VoteResultDto> getResultsByElection(Long electionId) {
        List<Candidate> candidates = candidateRepository.findByElectionId(electionId);
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

    public List<VoteResultDto> determineWinner(Long electionId) {
        List<Candidate> candidates = candidateRepository.findByElectionId(electionId);
        long maxVotes = 0;
        List<VoteResultDto> winners = new ArrayList<>();

        for (Candidate c : candidates) {
            long count = voteRepository.countByCandidateId(c.getId());
            if (count > maxVotes) {
                maxVotes = count;
                winners.clear();
                winners.add(new VoteResultDto(c.getId(), c.getName(), c.getParty(), count));
            } else if (count == maxVotes && count > 0) {
                winners.add(new VoteResultDto(c.getId(), c.getName(), c.getParty(), count));
            }
        }
        return winners;
    }
}
