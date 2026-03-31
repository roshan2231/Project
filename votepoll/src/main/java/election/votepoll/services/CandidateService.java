package election.votepoll.services;

import election.votepoll.models.Candidate;
import election.votepoll.models.Election;
import election.votepoll.models.Position;
import election.votepoll.payload.request.CandidateRequest;
import election.votepoll.repository.CandidateRepository;
import election.votepoll.repository.ElectionRepository;
import election.votepoll.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("null")
public class CandidateService {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    PositionRepository positionRepository;

    public Candidate addCandidate(CandidateRequest request) {
        Election election = electionRepository.findById(request.getElectionId()).orElseThrow();
        Position position = positionRepository.findById(request.getPositionId()).orElseThrow();

        Candidate candidate = new Candidate();
        candidate.setElection(election);
        candidate.setPosition(position);
        candidate.setName(request.getName());
        candidate.setParty(request.getParty());
        candidate.setSymbol(request.getSymbol());
        candidate.setDetails(request.getDetails());

        return candidateRepository.save(candidate);
    }

    public List<Candidate> getCandidatesByElectionAndPosition(Long electionId, Long positionId) {
        return candidateRepository.findByElectionIdAndPositionId(electionId, positionId);
    }

    public Candidate updateCandidate(Long id, CandidateRequest request) {
        Candidate candidate = candidateRepository.findById(id).orElseThrow();
        if (request.getName() != null) candidate.setName(request.getName());
        if (request.getParty() != null) candidate.setParty(request.getParty());
        if (request.getSymbol() != null) candidate.setSymbol(request.getSymbol());
        if (request.getDetails() != null) candidate.setDetails(request.getDetails());
        return candidateRepository.save(candidate);
    }

    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }
}
