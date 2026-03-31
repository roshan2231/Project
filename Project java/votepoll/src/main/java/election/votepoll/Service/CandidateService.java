package election.votepoll.Service;

import election.votepoll.Converter.EntityDtoConverter;
import election.votepoll.Exception.ResourceNotFoundException;
import election.votepoll.Model.Candidate;
import election.votepoll.Model.Dto.CandidateDto;
import election.votepoll.Repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private EntityDtoConverter converter;

    public CandidateDto addCandidate(CandidateDto dto) {
        Candidate candidate = converter.toCandidateEntity(dto);
        Candidate saved = candidateRepository.save(candidate);
        return converter.toCandidateDto(saved);
    }

    public List<CandidateDto> getCandidatesByElectionAndPosition(Long electionId, Long positionId) {
        return candidateRepository.findByElectionIdAndPositionId(electionId, positionId).stream()
                .map(converter::toCandidateDto)
                .collect(Collectors.toList());
    }
    
    public CandidateDto getCandidateById(Long id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + id));
        return converter.toCandidateDto(candidate);
    }

    public CandidateDto updateCandidate(Long id, CandidateDto dto) {
        Candidate existing = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + id));
        
        existing.setName(dto.getName());
        existing.setParty(dto.getParty());
        existing.setSymbol(dto.getSymbol());
        existing.setDetails(dto.getDetails());
        
        return converter.toCandidateDto(candidateRepository.save(existing));
    }

    public void deleteCandidate(Long id) {
        Candidate existing = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id " + id));
        candidateRepository.delete(existing);
    }
}
