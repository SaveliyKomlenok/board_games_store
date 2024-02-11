package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeReadDto;
import by.saveliykomlenok.boardgamesstore.entity.AccessoryType;
import by.saveliykomlenok.boardgamesstore.repositories.AccessoryTypeRepository;
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
public class AccessoryTypeService {
    private final AccessoryTypeRepository accessoryTypeRepository;
    private final ModelMapper mapper;

    public List<AccessoryTypeReadDto> findAll() {
        return accessoryTypeRepository.findAll().stream()
                .map(accessoryType -> mapper.map(accessoryType, AccessoryTypeReadDto.class))
                .toList();
    }

    public AccessoryTypeReadDto findById(Long id) {
        return accessoryTypeRepository.findById(id)
                .map(accessoryType -> mapper.map(accessoryType, AccessoryTypeReadDto.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public AccessoryTypeReadDto create(AccessoryTypeCreateEditDto accessoryTypeDto) {
        return Optional.of(accessoryTypeDto)
                .map(accessoryTypeCreateEditDto -> mapper.map(accessoryTypeDto, AccessoryType.class))
                .map(accessoryTypeRepository::save)
                .map(accessoryType -> mapper.map(accessoryType, AccessoryTypeReadDto.class))
                .orElse(null);
    }

    @Transactional
    public AccessoryTypeReadDto update(Long id, AccessoryTypeCreateEditDto accessoryTypeDto) {
        AccessoryType accessoryType = accessoryTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapper.map(accessoryTypeDto, accessoryType);
        accessoryTypeRepository.saveAndFlush(accessoryType);
        return mapper.map(accessoryType, AccessoryTypeReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        accessoryTypeRepository.findById(id)
                .map(entity -> {
                    accessoryTypeRepository.delete(entity);
                    accessoryTypeRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
