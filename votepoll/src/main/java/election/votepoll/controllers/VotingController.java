package election.votepoll.controllers;

import election.votepoll.payload.request.VoteRequest;
import election.votepoll.security.services.UserDetailsImpl;
import election.votepoll.services.VotingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/voting")
@PreAuthorize("hasRole('VOTER') or hasRole('ADMIN')")
public class VotingController {

    @Autowired
    VotingService votingService;

    @Autowired
    election.votepoll.util.DtoConverter dtoConverter;

    @GetMapping("/elections/active")
    public ResponseEntity<?> getActiveElections() {
        return ResponseEntity.ok(dtoConverter.toElectionDTOs(votingService.getActiveElections()));
    }

    @GetMapping("/elections/{electionId}/positions")
    public ResponseEntity<?> getPositionsByConstituency(@PathVariable Long electionId, @RequestParam String constituency) {
        return ResponseEntity.ok(dtoConverter.toPositionDTOs(votingService.getPositionsForConstituency(electionId, constituency)));
    }

    @GetMapping("/positions/{positionId}/candidates")
    public ResponseEntity<?> getCandidatesForPosition(@PathVariable Long positionId) {
        return ResponseEntity.ok(dtoConverter.toCandidateDTOs(votingService.getCandidates(positionId)));
    }

    @PostMapping("/vote")
    public ResponseEntity<?> castVote(@RequestBody VoteRequest voteRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(votingService.castVote(userDetails.getId(), voteRequest));
    }
}
