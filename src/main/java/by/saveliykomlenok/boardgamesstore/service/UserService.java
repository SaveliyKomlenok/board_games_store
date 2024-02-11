package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.user.UserCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.user.UserReadDto;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserReadDto.class))
                .toList();
    }

    public UserReadDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> mapper.map(user, UserReadDto.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public UserReadDto create(UserCreateEditDto userDto) {
        return Optional.of(userDto)
                .map(userCreateEditDto -> mapper.map(userDto, User.class))
                .map(userRepository::save)
                .map(user -> mapper.map(user, UserReadDto.class))
                .orElse(null);
    }

    @Transactional
    public UserReadDto update(Long id, UserCreateEditDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapper.map(userDto, user);
        userRepository.saveAndFlush(user);
        return mapper.map(user, UserReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
                .map(entity -> {
                    userRepository.delete(entity);
                    userRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
