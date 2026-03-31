package election.votepoll.repository;

import election.votepoll.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByVoterID(String voterID);
    Boolean existsByVoterID(String voterID);
    Boolean existsByEmail(String email);
}
