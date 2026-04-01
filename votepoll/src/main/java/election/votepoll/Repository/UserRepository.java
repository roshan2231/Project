package election.votepoll.Repository;

import election.votepoll.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByVoterId(String voterId);
    Optional<User> findByVoterId(String voterId);
    Optional<User> findByEmail(String email);
}
