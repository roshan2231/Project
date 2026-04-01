package election.votepoll.Service;

import election.votepoll.Converter.EntityDtoConverter;
import election.votepoll.Exception.ResourceNotFoundException;
import election.votepoll.Model.Election;
import election.votepoll.Model.Enum.Status;
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
public class ElectionServiceImp implements ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private EntityDtoConverter converter;

    public ElectionDto createElection(ElectionDto dto) {
        Election election = converter.toElectionEntity(dto);
        election.setStatus(Status.ACTIVE);
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
        Election found = electionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + id));

        found.setName(dto.getName());
        found.setDetails(dto.getDetails());
        found.setStartDate(dto.getStartDate());
        found.setEndDate(dto.getEndDate());
        found.setStatus(dto.getStatus() != null ? dto.getStatus() : found.getStatus());

        return converter.toElectionDto(electionRepository.save(found));
    }

    public void deleteElection(Long id) {
        Election found = electionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Election not found with id " + id));
        electionRepository.delete(found);
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
        Position found = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + id));

        found.setName(dto.getName());
        found.setConstituency(dto.getConstituency());

        return converter.toPositionDto(positionRepository.save(found));
    }

    public String deletePosition(Long id) {
        Position found = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + id));
        positionRepository.delete(found);
        return "Deleted Sucessfully";

    }
}
