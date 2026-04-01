package election.votepoll.Service;

import election.votepoll.Model.Dto.CandidateDto;

import java.util.List;

public interface CandidateService {

     CandidateDto addCandidate(CandidateDto dto);

     List<CandidateDto> getCandidatesByElectionAndPosition(Long electionId, Long positionId);

     CandidateDto getCandidateById(Long id);

     CandidateDto updateCandidate(Long id, CandidateDto dto);

     void deleteCandidate(Long id);
}
