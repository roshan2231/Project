package election.votepoll.util;

import election.votepoll.models.Candidate;
import election.votepoll.models.Election;
import election.votepoll.models.Position;
import election.votepoll.models.User;
import election.votepoll.payload.response.CandidateDTO;
import election.votepoll.payload.response.ElectionDTO;
import election.votepoll.payload.response.PositionDTO;
import election.votepoll.payload.response.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {

    public UserDTO toUserDTO(User user) {
        if (user == null) return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setVoterID(user.getVoterID());
        dto.setConstituency(user.getConstituency());
        dto.setRole(user.getRole());
        return dto;
    }

    public CandidateDTO toCandidateDTO(Candidate candidate) {
        if (candidate == null) return null;
        CandidateDTO dto = new CandidateDTO();
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

    public ElectionDTO toElectionDTO(Election election) {
        if (election == null) return null;
        ElectionDTO dto = new ElectionDTO();
        dto.setId(election.getId());
        dto.setName(election.getName());
        dto.setDetails(election.getDetails());
        dto.setStartDate(election.getStartDate());
        dto.setEndDate(election.getEndDate());
        dto.setStatus(election.getStatus());
        return dto;
    }

    public PositionDTO toPositionDTO(Position position) {
        if (position == null) return null;
        PositionDTO dto = new PositionDTO();
        dto.setId(position.getId());
        dto.setName(position.getName());
        dto.setConstituency(position.getConstituency());
        if (position.getElection() != null) {
            dto.setElectionId(position.getElection().getId());
        }
        return dto;
    }

    public List<CandidateDTO> toCandidateDTOs(List<Candidate> candidates) {
        return candidates.stream().map(this::toCandidateDTO).collect(Collectors.toList());
    }

    public List<ElectionDTO> toElectionDTOs(List<Election> elections) {
        return elections.stream().map(this::toElectionDTO).collect(Collectors.toList());
    }

    public List<PositionDTO> toPositionDTOs(List<Position> positions) {
        return positions.stream().map(this::toPositionDTO).collect(Collectors.toList());
    }
}
