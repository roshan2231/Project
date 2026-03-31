package election.votepoll.services;

import election.votepoll.models.*;
import election.votepoll.payload.request.VoteRequest;
import election.votepoll.payload.response.MessageResponse;
import election.votepoll.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@SuppressWarnings("null")
public class VotingService {

    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    public List<Election> getActiveElections() {
        return electionRepository.findByStatus(ElectionStatus.ACTIVE);
    }

    public List<Position> getPositionsForConstituency(Long electionId, String constituency) {
        return positionRepository.findByElectionIdAndConstituency(electionId, constituency);
    }

    public List<Candidate> getCandidates(Long positionId) {
        return candidateRepository.findByPositionId(positionId);
    }

    public MessageResponse castVote(Long userId, VoteRequest request) {
        Election election = electionRepository.findById(request.getElectionId()).orElseThrow(() -> new RuntimeException("Election not found"));
        if (election.getStatus() != ElectionStatus.ACTIVE) {
            return new MessageResponse("Error: Election is not active!");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Position position = positionRepository.findById(request.getPositionId()).orElseThrow(() -> new RuntimeException("Position not found"));
        
        // Ensure that position's constituency matches user's constituency
        if (!position.getConstituency().equalsIgnoreCase("All") && !position.getConstituency().equalsIgnoreCase(user.getConstituency())) {
            return new MessageResponse("Error: Cannot vote outside your constituency.");
        }

        // Check if user already voted for this position
        if (voteRepository.existsByUserIdAndPositionId(userId, request.getPositionId())) {
            return new MessageResponse("Error: You have already cast a vote for this position.");
        }

        Candidate candidate = candidateRepository.findById(request.getCandidateId()).orElseThrow(() -> new RuntimeException("Candidate not found"));

        Vote vote = new Vote();
        vote.setVoterId(user.getVoterID());
        vote.setUser(user);
        vote.setCandidate(candidate);
        vote.setElection(election);
        vote.setPosition(position);
        vote.setTimestamp(LocalDateTime.now());

        voteRepository.save(vote);

        return new MessageResponse("Vote cast successfully!");
    }
}
