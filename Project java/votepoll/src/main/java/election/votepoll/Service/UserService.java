package election.votepoll.Service;

import election.votepoll.Converter.EntityDtoConverter;
import election.votepoll.Exception.ResourceNotFoundException;
import election.votepoll.Model.User;
import election.votepoll.Model.Dto.UserDto;
import election.votepoll.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityDtoConverter converter;

    public UserDto registerUser(UserDto userDto) {
        if (userRepository.existsByVoterId(userDto.getVoterId())) {
            throw new IllegalArgumentException("User with voterId " + userDto.getVoterId() + " already exists!");
        }
        User user = converter.toUserEntity(userDto);
        User savedUser = userRepository.save(user);
        return converter.toUserDto(savedUser);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return converter.toUserDto(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(converter::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        
        existingUser.setName(userDto.getName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setConstituency(userDto.getConstituency());
        existingUser.setRole(userDto.getRole());
        if (userDto.getPassword() != null) {
            existingUser.setPassword(userDto.getPassword());
        }

        User updatedUser = userRepository.save(existingUser);
        return converter.toUserDto(updatedUser);
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(existingUser);
    }
}
