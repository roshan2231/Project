package election.votepoll.Service;

import election.votepoll.Converter.EntityDtoConverter;
import election.votepoll.Exception.ResourceNotFoundException;
import election.votepoll.Model.Election;
import election.votepoll.Model.Position;
import election.votepoll.Model.Dto.ElectionDto;
import election.votepoll.Model.Dto.PositionDto;
import election.votepoll.Repository.ElectionRepository;
import election.votepoll.Repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EntityDtoConverter converter;

    public ElectionDto createElection(ElectionDto dto) {
        Election election = converter.toElectionEntity(dto);
        election.setStatus("ACTIVE");
        Election saved = electionRepository.save(election);
        return converter.toElectionDto(saved);
    }

    public List<ElectionDto> getAllElections() {
        return electionRepository.findAll().stream()
                .map(converter::toElectionDto)
                .collect(Collectors.toList());
    }

    public ElectionDto getElectionById(Long id) {
        Election election = electionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + id));
        return converter.toElectionDto(election);
    }

    public ElectionDto updateElection(Long id, ElectionDto dto) {
        Election existing = electionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + id));

        existing.setName(dto.getName());
        existing.setDetails(dto.getDetails());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setStatus(dto.getStatus() != null ? dto.getStatus() : existing.getStatus());

        return converter.toElectionDto(electionRepository.save(existing));
    }

    public void deleteElection(Long id) {
        Election existing = electionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + id));
        electionRepository.delete(existing);
    }

    public PositionDto createPosition(PositionDto dto) {
        Position position = converter.toPositionEntity(dto);
        Position saved = positionRepository.save(position);
        return converter.toPositionDto(saved);
    }

    public List<PositionDto> getPositionsByElection(Long electionId) {
        return positionRepository.findByElectionId(electionId).stream()
                .map(converter::toPositionDto)
                .collect(Collectors.toList());
    }

    public List<PositionDto> getPositionsByElectionAndConstituency(Long electionId, String constituency) {
        return positionRepository.findByElectionIdAndConstituency(electionId, constituency).stream()
                .map(converter::toPositionDto)
                .collect(Collectors.toList());
    }

    public PositionDto updatePosition(Long id, PositionDto dto) {
        Position existing = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + id));

        existing.setName(dto.getName());
        existing.setConstituency(dto.getConstituency());

        return converter.toPositionDto(positionRepository.save(existing));
    }

    public void deletePosition(Long id) {
        Position existing = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + id));
        positionRepository.delete(existing);
    }
}
