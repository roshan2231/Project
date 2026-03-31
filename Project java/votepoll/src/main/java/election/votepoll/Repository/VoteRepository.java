package election.votepoll.Repository;

import election.votepoll.Model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    boolean existsByUserIdAndPositionId(Long userId, Long positionId);
    long countByCandidateId(Long candidateId);
    long countByElectionId(Long electionId);
    
    // Result calculation
    List<Vote> findByElectionIdAndPositionId(Long electionId, Long positionId);
}
