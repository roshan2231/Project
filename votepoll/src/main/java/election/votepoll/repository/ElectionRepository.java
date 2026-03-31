package election.votepoll.repository;

import election.votepoll.models.Election;
import election.votepoll.models.ElectionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Long> {
    List<Election> findByStatus(ElectionStatus status);
}
