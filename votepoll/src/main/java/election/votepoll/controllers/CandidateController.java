package election.votepoll.controllers;

import election.votepoll.payload.request.CandidateRequest;
import election.votepoll.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/candidates")
@PreAuthorize("hasRole('ADMIN')")
public class CandidateController {

    @Autowired
    CandidateService candidateService;

    @Autowired
    election.votepoll.util.DtoConverter dtoConverter;

    @PostMapping
    public ResponseEntity<?> addCandidate(@RequestBody CandidateRequest request) {
        return ResponseEntity.ok(dtoConverter.toCandidateDTO(candidateService.addCandidate(request)));
    }

    @GetMapping
    public ResponseEntity<?> getCandidates(@RequestParam Long electionId, @RequestParam Long positionId) {
        return ResponseEntity.ok(dtoConverter.toCandidateDTOs(candidateService.getCandidatesByElectionAndPosition(electionId, positionId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCandidate(@PathVariable Long id, @RequestBody CandidateRequest request) {
        return ResponseEntity.ok(dtoConverter.toCandidateDTO(candidateService.updateCandidate(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok().body("{\"message\": \"Candidate deleted\"}");
    }
}
