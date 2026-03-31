package election.votepoll.Controller;

import election.votepoll.Model.Dto.VoteDto;
import election.votepoll.Service.VotingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/votes")
public class VotingController {

    @Autowired
    private VotingService votingService;

    @PostMapping
    public ResponseEntity<VoteDto> castVote(@Valid @RequestBody VoteDto dto) {
        return new ResponseEntity<>(votingService.castVote(dto), HttpStatus.CREATED);
    }
}
