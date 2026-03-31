package election.votepoll.services;

import election.votepoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResultService {

    @Autowired
    VoteRepository voteRepository;

    public Long getVoteCountForCandidate(Long candidateId) {
        return voteRepository.countByCandidateId(candidateId);
    }

    public Map<String, Long> getElectionResultsByParty(Long electionId) {
        List<Object[]> results = voteRepository.countVotesByPartyForElection(electionId);
        Map<String, Long> partyResults = new HashMap<>();
        for (Object[] result : results) {
            String party = (String) result[0];
            Long count = (Long) result[1];
            partyResults.put(party, count);
        }
        return partyResults;
    }
}
