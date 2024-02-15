package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.entity.Accessory;
import by.saveliykomlenok.boardgamesstore.entity.AccessoryType;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import by.saveliykomlenok.boardgamesstore.repositoriy.AccessoryRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.accessory.AccessoryIsExistsException;
import by.saveliykomlenok.boardgamesstore.util.exception.accessory.AccessoryMissingException;
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
public class AccessoryService {
    private final AccessoryRepository accessoryRepository;
    private final AccessoryTypeService accessoryTypeService;
    private final ManufacturerService manufacturerService;
    private final ModelMapper mapper;

    public List<AccessoryReadDto> findAll() {
        return accessoryRepository.findAll().stream()
                .map(accessory -> mapper.map(accessory, AccessoryReadDto.class))
                .toList();
    }

    public AccessoryReadDto findById(Long id) {
        return accessoryRepository.findById(id)
                .map(accessory -> mapper.map(accessory, AccessoryReadDto.class))
                .orElseThrow(() -> new AccessoryMissingException("Accessory is out of stock"));
    }

    @Transactional
    public AccessoryReadDto create(AccessoryCreateEditDto accessoryDto) {
        Accessory accessory = Optional.of(accessoryDto)
                .map(entity -> mapper.map(entity, Accessory.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
        accessory.setManufacturer(mapper.map(manufacturerService.findById(accessoryDto.getManufacturer()), Manufacturer.class));
        accessory.setAccessoryType(mapper.map(accessoryTypeService.findById(accessoryDto.getAccessoryType()), AccessoryType.class));

        if (validateCreateUpdate(accessoryDto)){
            throw new AccessoryIsExistsException("Accessory is already exists");
        }
        accessoryRepository.save(accessory);
        return mapper.map(accessory, AccessoryReadDto.class);
    }

    @Transactional
    public AccessoryReadDto update(Long id, AccessoryCreateEditDto accessoryDto) {
        Accessory accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new AccessoryMissingException("Accessory is out of stock"));

        if (validateCreateUpdate(id, accessoryDto)){
            throw new AccessoryIsExistsException("Accessory is already exists");
        }

        mapper.map(accessoryDto, accessory);

        accessory.setManufacturer(mapper.map(manufacturerService.findById(accessoryDto.getManufacturer()), Manufacturer.class));
        accessory.setAccessoryType(mapper.map(accessoryTypeService.findById(accessoryDto.getAccessoryType()), AccessoryType.class));
        accessoryRepository.saveAndFlush(accessory);
        return mapper.map(accessory, AccessoryReadDto.class);
    }

    private boolean validateCreateUpdate(Long id, AccessoryCreateEditDto accessoryDto){
        Optional<Accessory> tempAccessory = getAccessory(accessoryDto);
        return tempAccessory.isPresent() && !tempAccessory.get().getId().equals(id);
    }

    private boolean validateCreateUpdate(AccessoryCreateEditDto accessoryDto){
        Optional<Accessory> tempAccessory = getAccessory(accessoryDto);
        return tempAccessory.isPresent();
    }

    private Optional<Accessory> getAccessory(AccessoryCreateEditDto accessoryDto) {
        return accessoryRepository.findAccessoryByNameAndPriceAndAmountAndManufacturerIdAndAccessoryTypeId(
                accessoryDto.getName(),
                accessoryDto.getPrice(),
                accessoryDto.getAmount(),
                accessoryDto.getManufacturer(),
                accessoryDto.getAccessoryType());
    }

    @Transactional
    public void delete(Long id) {
        accessoryRepository.findById(id)
                .map(accessory -> {
                    accessoryRepository.delete(accessory);
                    accessoryRepository.flush();
                    return true;
                }).orElseThrow(() -> new AccessoryMissingException("Accessory is out of stock"));
    }
}
