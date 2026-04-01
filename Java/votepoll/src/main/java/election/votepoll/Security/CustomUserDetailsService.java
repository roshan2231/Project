package election.votepoll.Security;

import election.votepoll.Model.User;
import election.votepoll.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String voterId) throws UsernameNotFoundException {
        User user = userRepository.findByVoterId(voterId).orElseThrow(
                () -> new UsernameNotFoundException("User not found with voterId: " + voterId));

        return new CustomUserDetails(user);
    }
}
