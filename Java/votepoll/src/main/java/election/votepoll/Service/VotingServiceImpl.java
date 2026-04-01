package election.votepoll.Service;

import election.votepoll.Converter.EntityDtoConverter;
import election.votepoll.Exception.DuplicateVoteException;
import election.votepoll.Model.Vote;
import election.votepoll.Model.Enums.Status;
import election.votepoll.Model.Dto.VoteDto;
import election.votepoll.Repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VotingServiceImpl implements VotingService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private EntityDtoConverter converter;

    public VoteDto castVote(VoteDto dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String authenticatedVoterId = authentication.getName();

        if (!dto.getVoterId().equals(authenticatedVoterId)) {
            throw new IllegalArgumentException("Unauthorized voting attempt: voterId mismatch.");
        }

        if (voteRepository.existsByUserIdAndPositionId(dto.getUserId(), dto.getPositionId())) {
            throw new DuplicateVoteException("User has already voted for this position!");
        }

        Vote vote = converter.toVoteEntity(dto);

        if (vote.getElection().getStatus() != Status.ACTIVE) {
            throw new IllegalArgumentException("Cannot vote in an inactive election!");
        }

        Vote saved = voteRepository.save(vote);
        return converter.toVoteDto(saved);
    }
}
