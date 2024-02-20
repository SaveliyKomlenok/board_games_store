package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeReadDto;
import by.saveliykomlenok.boardgamesstore.entity.AccessoryType;
import by.saveliykomlenok.boardgamesstore.repositoriy.AccessoryTypeRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.accessory.AccessoryTypeIsExistsException;
import by.saveliykomlenok.boardgamesstore.util.exception.accessory.AccessoryTypeMissingException;
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
                .orElseThrow(() -> new AccessoryTypeMissingException("Accessory type doesn't exist"));
    }

    @Transactional
    public AccessoryTypeReadDto create(AccessoryTypeCreateEditDto accessoryTypeDto) {
        AccessoryType accessoryType = Optional.of(accessoryTypeDto)
                .map(accessoryTypeCreateEditDto -> mapper.map(accessoryTypeDto, AccessoryType.class))
                .orElseThrow();
        if (validateCreateUpdate(accessoryTypeDto)) {
            throw new AccessoryTypeIsExistsException("Accessory type is already exists");
        }

        return mapper.map(accessoryTypeRepository.save(accessoryType), AccessoryTypeReadDto.class);
    }


    @Transactional
    public AccessoryTypeReadDto update(Long id, AccessoryTypeCreateEditDto accessoryTypeDto) {
        AccessoryType accessoryType = accessoryTypeRepository.findById(id)
                .orElseThrow(() -> new AccessoryTypeMissingException("Accessory type doesn't exist"));
        if (validateCreateUpdate(id, accessoryTypeDto)) {
            throw new AccessoryTypeIsExistsException("Accessory type is already exists");
        }
        mapper.map(accessoryTypeDto, accessoryType);

        return mapper.map(accessoryTypeRepository.saveAndFlush(accessoryType), AccessoryTypeReadDto.class);
    }

    private boolean validateCreateUpdate(Long id, AccessoryTypeCreateEditDto accessoryTypeDto) {
        Optional<AccessoryType> tempAccessoryType = accessoryTypeRepository.findAccessoryTypeByName(accessoryTypeDto.getName());
        return tempAccessoryType.isPresent() && !tempAccessoryType.get().getId().equals(id);
    }

    private boolean validateCreateUpdate(AccessoryTypeCreateEditDto accessoryTypeDto) {
        Optional<AccessoryType> tempAccessoryType = accessoryTypeRepository.findAccessoryTypeByName(accessoryTypeDto.getName());
        return tempAccessoryType.isPresent();
    }

    @Transactional
    public void delete(Long id) {
        accessoryTypeRepository.findById(id)
                .map(entity -> {
                    accessoryTypeRepository.delete(entity);
                    accessoryTypeRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new AccessoryTypeMissingException("Accessory type doesn't exist"));
    }
}
