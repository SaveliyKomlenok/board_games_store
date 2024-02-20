package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.user.UserCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserReadDto;
import by.saveliykomlenok.boardgamesstore.entity.Role;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.repositoriy.UserRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.user.UserIsExistsException;
import by.saveliykomlenok.boardgamesstore.util.exception.user.UserMissingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserReadDto.class))
                .toList();
    }

    public UserReadDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> mapper.map(user, UserReadDto.class))
                .orElseThrow(() -> new UserMissingException("User doesn't exist"));
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserMissingException("User doesn't exist"));
    }

    @Transactional
    public User create(UserCreateEditDto userDto){
        User user = Optional.of(userDto)
                .map(userCreateEditDto -> mapper.map(userDto, User.class))
                .orElseThrow();
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()){
            throw new UserIsExistsException("Username is already exist");
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        return userRepository.save(user);
    }

    @Transactional
    public UserReadDto update(Long id, UserCreateEditDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserMissingException("User doesn't exist"));
        mapper.map(userDto, user);
        userRepository.saveAndFlush(user);
        return mapper.map(user, UserReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
                .map(user -> {
                    userRepository.delete(user);
                    userRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new UserMissingException("User doesn't exist"));
    }
}
