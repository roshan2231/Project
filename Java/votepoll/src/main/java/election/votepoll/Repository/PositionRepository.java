package election.votepoll.Repository;

import election.votepoll.Model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    List<Position> findByElectionId(Long electionId);
    List<Position> findByElectionIdAndConstituency(Long electionId, String constituency);
}
