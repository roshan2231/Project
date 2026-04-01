package election.votepoll.Converter;

import election.votepoll.Model.*;
import election.votepoll.Model.Dto.*;
import election.votepoll.Repository.*;
import election.votepoll.Exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EntityDtoConverter {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private PositionRepository positionRepository;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public UserDto toUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setVoterId(user.getVoterId());
        dto.setConstituency(user.getConstituency());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }

    public User toUserEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setVoterId(dto.getVoterId());
        user.setConstituency(dto.getConstituency());
        user.setRole(dto.getRole());
        if (dto.getStatus() != null) {
            user.setStatus(dto.getStatus());
        }
        return user;
    }

    public ElectionDto toElectionDto(Election election) {
        ElectionDto dto = new ElectionDto();
        dto.setId(election.getId());
        dto.setName(election.getName());
        dto.setDetails(election.getDetails());
        dto.setStartDate(election.getStartDate());
        dto.setEndDate(election.getEndDate());
        dto.setStatus(election.getStatus());
        return dto;
    }

    public Election toElectionEntity(ElectionDto dto) {
        Election election = new Election();
        election.setId(dto.getId());
        election.setName(dto.getName());
        election.setDetails(dto.getDetails());
        election.setStartDate(dto.getStartDate());
        election.setEndDate(dto.getEndDate());
        if (dto.getStatus() != null) {
            election.setStatus(dto.getStatus());
        }
        return election;
    }

    public PositionDto toPositionDto(Position position) {
        PositionDto dto = new PositionDto();
        dto.setId(position.getId());
        dto.setName(position.getName());
        dto.setConstituency(position.getConstituency());
        if (position.getElection() != null) {
            dto.setElectionId(position.getElection().getId());
        }
        return dto;
    }

    public Position toPositionEntity(PositionDto dto) {
        Position position = new Position();
        position.setId(dto.getId());
        position.setName(dto.getName());
        position.setConstituency(dto.getConstituency());
        if (dto.getElectionId() != null) {
            Election election = electionRepository.findById(dto.getElectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + dto.getElectionId()));
            position.setElection(election);
        }
        return position;
    }

    public CandidateDto toCandidateDto(Candidate candidate) {
        CandidateDto dto = new CandidateDto();
        dto.setId(candidate.getId());
        dto.setName(candidate.getName());
        dto.setParty(candidate.getParty());
        dto.setSymbol(candidate.getSymbol());
        dto.setDetails(candidate.getDetails());
        if (candidate.getElection() != null) {
            dto.setElectionId(candidate.getElection().getId());
        }
        if (candidate.getPosition() != null) {
            dto.setPositionId(candidate.getPosition().getId());
        }
        return dto;
    }

    public Candidate toCandidateEntity(CandidateDto dto) {
        Candidate candidate = new Candidate();
        candidate.setId(dto.getId());
        candidate.setName(dto.getName());
        candidate.setParty(dto.getParty());
        candidate.setSymbol(dto.getSymbol());
        candidate.setDetails(dto.getDetails());
        
        if (dto.getElectionId() != null) {
            Election election = electionRepository.findById(dto.getElectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + dto.getElectionId()));
            candidate.setElection(election);
        }
        
        if (dto.getPositionId() != null) {
            Position position = positionRepository.findById(dto.getPositionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + dto.getPositionId()));
            candidate.setPosition(position);
        }
        return candidate;
    }

    public VoteDto toVoteDto(Vote vote) {
        VoteDto dto = new VoteDto();
        dto.setId(vote.getId());
        dto.setVoterId(vote.getVoterId());
        dto.setTimestamp(vote.getTimestamp());
        if (vote.getUser() != null) dto.setUserId(vote.getUser().getId());
        if (vote.getCandidate() != null) dto.setCandidateId(vote.getCandidate().getId());
        if (vote.getElection() != null) dto.setElectionId(vote.getElection().getId());
        if (vote.getPosition() != null) dto.setPositionId(vote.getPosition().getId());
        return dto;
    }

    public Vote toVoteEntity(VoteDto dto) {
        Vote vote = new Vote();
        vote.setId(dto.getId());
        vote.setVoterId(dto.getVoterId());
        if (dto.getTimestamp() != null) {
            vote.setTimestamp(dto.getTimestamp());
        } else {
            vote.setTimestamp(LocalDateTime.now());
        }

        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + dto.getUserId()));
            vote.setUser(user);
        }
        
        if (dto.getCandidateId() != null) {
            Candidate candidate = candidateRepository.findById(dto.getCandidateId())
                    .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + dto.getCandidateId()));
            vote.setCandidate(candidate);
        }

        if (dto.getElectionId() != null) {
            Election election = electionRepository.findById(dto.getElectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + dto.getElectionId()));
            vote.setElection(election);
        }
        
        if (dto.getPositionId() != null) {
            Position position = positionRepository.findById(dto.getPositionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + dto.getPositionId()));
            vote.setPosition(position);
        }
        return vote;
    }
}
