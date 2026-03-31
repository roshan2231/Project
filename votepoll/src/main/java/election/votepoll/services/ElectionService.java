package election.votepoll.services;

import election.votepoll.models.Election;
import election.votepoll.models.ElectionStatus;
import election.votepoll.models.Position;
import election.votepoll.payload.request.ElectionRequest;
import election.votepoll.payload.request.PositionRequest;
import election.votepoll.repository.ElectionRepository;
import election.votepoll.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@SuppressWarnings("null")
public class ElectionService {

    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    PositionRepository positionRepository;

    public Election createElection(ElectionRequest request) {
        Election election = new Election();
        election.setName(request.getName());
        election.setDetails(request.getDetails());
        election.setStartDate(request.getStartDate());
        election.setEndDate(request.getEndDate());
        return electionRepository.save(election);
    }

    public List<Election> getAllElections() {
        return electionRepository.findAll();
    }

    public Optional<Election> getElectionById(Long id) {
        return electionRepository.findById(id);
    }

    public Election updateElection(Long id, ElectionRequest request) {
        Election election = electionRepository.findById(id).orElseThrow();
        if (request.getName() != null) election.setName(request.getName());
        if (request.getDetails() != null) election.setDetails(request.getDetails());
        if (request.getStartDate() != null) election.setStartDate(request.getStartDate());
        if (request.getEndDate() != null) election.setEndDate(request.getEndDate());
        return electionRepository.save(election);
    }

    public Election changeElectionStatus(Long id, ElectionStatus status) {
        Election election = electionRepository.findById(id).orElseThrow();
        election.setStatus(status);
        return electionRepository.save(election);
    }

    public void deleteElection(Long id) {
        electionRepository.deleteById(id);
    }

    public Position createPosition(PositionRequest request) {
        Election election = electionRepository.findById(request.getElectionId()).orElseThrow();
        Position position = new Position();
        position.setName(request.getName());
        position.setConstituency(request.getConstituency());
        position.setElection(election);
        return positionRepository.save(position);
    }

    public Position updatePosition(Long id, PositionRequest request) {
        Position position = positionRepository.findById(id).orElseThrow();
        if (request.getName() != null) position.setName(request.getName());
        if (request.getConstituency() != null) position.setConstituency(request.getConstituency());
        return positionRepository.save(position);
    }

    public void deletePosition(Long id) {
        positionRepository.deleteById(id);
    }

    public List<Position> getPositionsByElection(Long electionId) {
        return positionRepository.findByElectionId(electionId);
    }
}
