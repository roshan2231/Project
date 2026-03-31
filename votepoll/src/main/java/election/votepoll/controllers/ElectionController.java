package election.votepoll.controllers;

import election.votepoll.models.ElectionStatus;
import election.votepoll.payload.request.ElectionRequest;
import election.votepoll.payload.request.PositionRequest;
import election.votepoll.services.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/elections")
@PreAuthorize("hasRole('ADMIN')")
public class ElectionController {

    @Autowired
    ElectionService electionService;

    @Autowired
    election.votepoll.util.DtoConverter dtoConverter;

    @PostMapping
    public ResponseEntity<?> createElection(@RequestBody ElectionRequest request) {
        return ResponseEntity.ok(dtoConverter.toElectionDTO(electionService.createElection(request)));
    }

    @GetMapping
    public ResponseEntity<?> getAllElections() {
        return ResponseEntity.ok(dtoConverter.toElectionDTOs(electionService.getAllElections()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getElectionById(@PathVariable Long id) {
        return electionService.getElectionById(id)
                .map(dtoConverter::toElectionDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateElection(@PathVariable Long id, @RequestBody ElectionRequest request) {
        return ResponseEntity.ok(dtoConverter.toElectionDTO(electionService.updateElection(id, request)));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateElection(@PathVariable Long id) {
        return ResponseEntity.ok(dtoConverter.toElectionDTO(electionService.changeElectionStatus(id, ElectionStatus.ACTIVE)));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateElection(@PathVariable Long id) {
        return ResponseEntity.ok(dtoConverter.toElectionDTO(electionService.changeElectionStatus(id, ElectionStatus.INACTIVE)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteElection(@PathVariable Long id) {
        electionService.deleteElection(id);
        return ResponseEntity.ok().body("{\"message\": \"Election deleted\"}");
    }

    @PostMapping("/positions")
    public ResponseEntity<?> createPosition(@RequestBody PositionRequest request) {
        return ResponseEntity.ok(dtoConverter.toPositionDTO(electionService.createPosition(request)));
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<?> updatePosition(@PathVariable Long id, @RequestBody PositionRequest request) {
        return ResponseEntity.ok(dtoConverter.toPositionDTO(electionService.updatePosition(id, request)));
    }

    @DeleteMapping("/positions/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable Long id) {
        electionService.deletePosition(id);
        return ResponseEntity.ok().body("{\"message\": \"Position deleted\"}");
    }

    @GetMapping("/{electionId}/positions")
    public ResponseEntity<?> getPositionsInElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(dtoConverter.toPositionDTOs(electionService.getPositionsByElection(electionId)));
    }
}
