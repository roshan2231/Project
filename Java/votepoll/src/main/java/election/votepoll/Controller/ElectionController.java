package election.votepoll.Controller;

import election.votepoll.Model.Dto.ElectionDto;
import election.votepoll.Model.Dto.PositionDto;
import election.votepoll.Service.ElectionService;
import election.votepoll.Service.ElectionServiceImp;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elections")
public class ElectionController {

    @Autowired
    private ElectionService electionService;

    @PostMapping
    public ResponseEntity<ElectionDto> createElection(@Valid @RequestBody ElectionDto dto) {
        return new ResponseEntity<>(electionService.createElection(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ElectionDto>> getAllElections() {
        return ResponseEntity.ok(electionService.getAllElections());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectionDto> getElectionById(@PathVariable Long id) {
        return ResponseEntity.ok(electionService.getElectionById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElectionDto> updateElection(@PathVariable Long id, @Valid @RequestBody ElectionDto dto) {
        return ResponseEntity.ok(electionService.updateElection(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElection(@PathVariable Long id) {
        electionService.deleteElection(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/positions")
    public ResponseEntity<PositionDto> createPosition(@Valid @RequestBody PositionDto dto) {
        return new ResponseEntity<>(electionService.createPosition(dto), HttpStatus.CREATED);
    }

    @GetMapping("/{electionId}/positions")
    public ResponseEntity<List<PositionDto>> getPositionsByElection(@PathVariable Long electionId) {
        return ResponseEntity.ok(electionService.getPositionsByElection(electionId));
    }
    
    @GetMapping("/{electionId}/positions/constituency/{constituency}")
    public ResponseEntity<List<PositionDto>> getPositionsByConstituency(@PathVariable Long electionId, @PathVariable String constituency) {
        return ResponseEntity.ok(electionService.getPositionsByElectionAndConstituency(electionId, constituency));
    }

    @PutMapping("/positions/{id}")
    public ResponseEntity<PositionDto> updatePosition(@PathVariable Long id, @Valid @RequestBody PositionDto dto) {
        return ResponseEntity.ok(electionService.updatePosition(id, dto));
    }

    @DeleteMapping("/positions/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Long id) {
        electionService.deletePosition(id);
        return ResponseEntity.noContent().build();
    }
}
