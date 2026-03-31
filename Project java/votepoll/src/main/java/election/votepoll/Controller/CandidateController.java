package election.votepoll.Controller;

import election.votepoll.Model.Dto.CandidateDto;
import election.votepoll.Service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateDto> addCandidate(@Valid @RequestBody CandidateDto dto) {
        return new ResponseEntity<>(candidateService.addCandidate(dto), HttpStatus.CREATED);
    }

    @GetMapping("/election/{electionId}/position/{positionId}")
    public ResponseEntity<List<CandidateDto>> getCandidates(@PathVariable Long electionId, @PathVariable Long positionId) {
        return ResponseEntity.ok(candidateService.getCandidatesByElectionAndPosition(electionId, positionId));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CandidateDto> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateDto> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateDto dto) {
        return ResponseEntity.ok(candidateService.updateCandidate(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}
