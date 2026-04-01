package election.votepoll.Service;

import election.votepoll.Model.Dto.ElectionDto;
import election.votepoll.Model.Dto.PositionDto;

import java.util.List;

public interface ElectionService {

    ElectionDto createElection(ElectionDto dto);

     List<ElectionDto> getAllElections();

     ElectionDto getElectionById(Long id);

     ElectionDto updateElection(Long id, ElectionDto dto);

     void deleteElection(Long id);

     PositionDto createPosition(PositionDto dto);

     List<PositionDto> getPositionsByElection(Long electionId);

     List<PositionDto> getPositionsByElectionAndConstituency(Long electionId, String constituency);

     PositionDto updatePosition(Long id, PositionDto dto);

     String deletePosition(Long id);
}
