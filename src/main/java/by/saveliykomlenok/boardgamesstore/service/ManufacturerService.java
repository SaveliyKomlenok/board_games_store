package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.repositories.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ModelMapper mapper;

    public List<ManufacturerReadDto> findAll(){
        return manufacturerRepository.findAll().stream()
                .map(manufacturer -> mapper.map(manufacturer, ManufacturerReadDto.class))
                .toList();
    }

    public Optional<ManufacturerReadDto> findById(Long id){
        return Optional.of(manufacturerRepository.findById(id)
                        .map(manufacturer -> mapper.map(manufacturer, ManufacturerReadDto.class)))
                .orElse(null);
    }
}
