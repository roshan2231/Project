package election.votepoll.repository;

import election.votepoll.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findByElectionIdAndPositionId(Long electionId, Long positionId);
    List<Candidate> findByPositionId(Long positionId);
}
