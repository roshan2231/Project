package election.votepoll.controllers;

import election.votepoll.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/results")
@PreAuthorize("hasRole('VOTER') or hasRole('ADMIN')")
public class ResultController {

    @Autowired
    ResultService resultService;

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<?> getVoteCountForCandidate(@PathVariable Long candidateId) {
        return ResponseEntity.ok(resultService.getVoteCountForCandidate(candidateId));
    }

    @GetMapping("/election/{electionId}/party")
    public ResponseEntity<?> getElectionResultsByParty(@PathVariable Long electionId) {
        return ResponseEntity.ok(resultService.getElectionResultsByParty(electionId));
    }
}
