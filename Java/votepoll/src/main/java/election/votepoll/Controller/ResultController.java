package election.votepoll.Controller;

import election.votepoll.Model.Dto.VoteResultDto;
import election.votepoll.Service.ResultServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
public class ResultController {

    @Autowired
    private ResultServiceImpl resultService;

    @GetMapping("/election/{electionId}/position/{positionId}")
    public ResponseEntity<List<VoteResultDto>> getResultsByPosition(@PathVariable Long electionId, @PathVariable Long positionId) {
        return ResponseEntity.ok(resultService.getResultsByElectionAndPosition(electionId, positionId));
    }

    @GetMapping("/election/{electionId}/total")
    public ResponseEntity<Long> getTotalVotesInElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(resultService.getTotalVotesInElection(electionId));
    }
}
