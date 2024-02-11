package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import by.saveliykomlenok.boardgamesstore.repositories.ManufacturerRepository;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public ManufacturerReadDto create(ManufacturerCreateEditDto manufacturerDto) {
        return Optional.of(manufacturerDto)
                .map(manufacturerCreateEditDto -> mapper.map(manufacturerDto, Manufacturer.class))
                .map(manufacturerRepository::save)
                .map(manufacturer -> mapper.map(manufacturer, ManufacturerReadDto.class))
                .orElseThrow();
    }

    @Transactional
    public ManufacturerReadDto update(Long id, ManufacturerCreateEditDto manufacturerDto) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapper.map(manufacturerDto, manufacturer);
        manufacturerRepository.saveAndFlush(manufacturer);
        return mapper.map(manufacturer, ManufacturerReadDto.class);

    }

    @Transactional
    public void delete(Long id) {
        manufacturerRepository.findById(id)
                .map(manufacturer -> {
                    manufacturerRepository.delete(manufacturer);
                    manufacturerRepository.flush();
                    return true;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
