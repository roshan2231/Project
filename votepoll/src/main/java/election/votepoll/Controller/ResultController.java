package election.votepoll.Controller;

import election.votepoll.Model.Candidate;
import election.votepoll.Model.Dto.VoteResultDto;
import election.votepoll.Repository.CandidateRepository;
import election.votepoll.Service.ResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultServiceImpl resultService;

    @Autowired
    private CandidateRepository candidateRepository;

    @GetMapping("/election/{electionId}/position/{positionId}")
    public ResponseEntity<List<VoteResultDto>> getResultsByPosition(@PathVariable Long electionId,
            @PathVariable Long positionId) {
        return ResponseEntity.ok(resultService.getResultsByElectionAndPosition(electionId, positionId));
    }

    @GetMapping("/election/{electionId}")
    public ResponseEntity<List<VoteResultDto>> getResultsByElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(resultService.getResultsByElection(electionId));
    }

    @GetMapping("/election/{electionId}/total")
    public ResponseEntity<Long> getTotalVotesInElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(resultService.getTotalVotesInElection(electionId));
    }

    @GetMapping("/election/{electionId}/winner")
    public ResponseEntity<List<VoteResultDto>> getWinner(@PathVariable Long electionId) {
        List<VoteResultDto> winners = resultService.determineWinner(electionId);

        if (winners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(winners);
    }
}
