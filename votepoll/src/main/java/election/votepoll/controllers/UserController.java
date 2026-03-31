package election.votepoll.controllers;

import election.votepoll.models.User;
import election.votepoll.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    election.votepoll.util.DtoConverter dtoConverter;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('VOTER') or hasRole('ADMIN')")
    public ResponseEntity<?> getUserDetails(@org.springframework.lang.NonNull @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(dtoConverter.toUserDTO(user.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
