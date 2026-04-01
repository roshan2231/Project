package election.votepoll.Service;

import election.votepoll.Converter.EntityDtoConverter;
import election.votepoll.Exception.DuplicateVoteException;
import election.votepoll.Model.Candidate;
import election.votepoll.Model.Vote;
import election.votepoll.Model.Dto.VoteDto;
import election.votepoll.Repository.VoteRepository;
import election.votepoll.Repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VotingServiceImpl implements  VotingService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private EntityDtoConverter converter;

    public VoteDto castVote(VoteDto dto) {
        if (voteRepository.existsByUserIdAndPositionId(dto.getUserId(), dto.getPositionId())) {
            throw new DuplicateVoteException("User has already voted for this position!");
        }

        Vote vote = converter.toVoteEntity(dto);
        Vote saved = voteRepository.save(vote);

        return converter.toVoteDto(saved);
    }
}
