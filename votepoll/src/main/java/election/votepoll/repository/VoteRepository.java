package election.votepoll.repository;

import election.votepoll.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Boolean existsByUserIdAndPositionId(Long userId, Long positionId);
    
    Long countByCandidateId(Long candidateId);
    
    @Query("SELECT v.candidate.party, COUNT(v) FROM Vote v WHERE v.election.id = :electionId GROUP BY v.candidate.party")
    List<Object[]> countVotesByPartyForElection(Long electionId);
}
