package election.votepoll.services;

import election.votepoll.models.Role;
import election.votepoll.models.User;
import election.votepoll.payload.request.LoginRequest;
import election.votepoll.payload.request.SignupRequest;
import election.votepoll.payload.response.JwtResponse;
import election.votepoll.payload.response.MessageResponse;
import election.votepoll.repository.UserRepository;
import election.votepoll.security.jwt.JwtUtils;
import election.votepoll.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    public JwtResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getVoterID(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getVoterID(),
                userDetails.getName(),
                userDetails.getEmail(),
                role);
    }

    public MessageResponse registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByVoterID(signUpRequest.getVoterID())) {
            return new MessageResponse("Error: Voter ID is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setVoterID(signUpRequest.getVoterID());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setConstituency(signUpRequest.getConstituency());

        if (signUpRequest.getRole() != null && signUpRequest.getRole().equalsIgnoreCase("admin")) {
            user.setRole(Role.ROLE_ADMIN);
        } else {
            user.setRole(Role.ROLE_VOTER);
        }

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }
}
