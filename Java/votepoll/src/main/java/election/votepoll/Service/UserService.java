package election.votepoll.Service;

import election.votepoll.Model.Dto.UserDto;

import java.util.List;

public interface UserService {

     UserDto registerUser(UserDto userDto);

     UserDto getUserById(Long id);

     List<UserDto> getAllUsers();

     UserDto updateUser(Long id, UserDto userDto);

     String deleteUser(Long id);
}
