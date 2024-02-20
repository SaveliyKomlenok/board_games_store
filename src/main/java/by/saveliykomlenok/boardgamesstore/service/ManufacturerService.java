package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import by.saveliykomlenok.boardgamesstore.repositoriy.ManufacturerRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.manufacturer.ManufacturerIsExistsException;
import by.saveliykomlenok.boardgamesstore.util.exception.manufacturer.ManufacturerMissingException;
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
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ModelMapper mapper;

    public List<ManufacturerReadDto> findAll() {
        return manufacturerRepository.findAll().stream()
                .map(manufacturer -> mapper.map(manufacturer, ManufacturerReadDto.class))
                .toList();
    }

    public ManufacturerReadDto findById(Long id) {
        return manufacturerRepository.findById(id)
                .map(manufacturer -> mapper.map(manufacturer, ManufacturerReadDto.class))
                .orElseThrow(() -> new ManufacturerMissingException("Manufacturer doesn't exist"));
    }

    @Transactional
    public ManufacturerReadDto create(ManufacturerCreateEditDto manufacturerDto) {
        Manufacturer manufacturer = Optional.of(manufacturerDto)
                .map(manufacturerCreateEditDto -> mapper.map(manufacturerDto, Manufacturer.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));

        if(validateCreateUpdate(manufacturerDto)){
            throw new ManufacturerIsExistsException("Manufacturer is already exist");
        }

        return mapper.map(manufacturerRepository.save(manufacturer), ManufacturerReadDto.class);
    }

    @Transactional
    public ManufacturerReadDto update(Long id, ManufacturerCreateEditDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ManufacturerMissingException("Manufacturer doesn't exist"));

        if(validateCreateUpdate(id, manufacturerDto)){
            throw new ManufacturerIsExistsException("Manufacturer is already exist");
        }
        mapper.map(manufacturerDto, manufacturer);

        return mapper.map(manufacturerRepository.saveAndFlush(manufacturer), ManufacturerReadDto.class);
    }

    private boolean validateCreateUpdate(Long id, ManufacturerCreateEditDto manufacturerDto){
        Optional<Manufacturer> tempManufacturer = manufacturerRepository.findManufacturerByName(manufacturerDto.getName());
        return tempManufacturer.isPresent() && !tempManufacturer.get().getId().equals(id);
    }

    private boolean validateCreateUpdate(ManufacturerCreateEditDto manufacturerDto){
        Optional<Manufacturer> tempManufacturer = manufacturerRepository.findManufacturerByName(manufacturerDto.getName());
        return tempManufacturer.isPresent();
    }

    @Transactional
    public void delete(Long id) {
        manufacturerRepository.findById(id)
                .map(manufacturer -> {
                    manufacturerRepository.delete(manufacturer);
                    manufacturerRepository.flush();
                    return true;
                }).orElseThrow(() -> new ManufacturerMissingException("Manufacturer doesn't exist"));
    }
}
