package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.BoardGameTypeReadDto;
import by.saveliykomlenok.boardgamesstore.dto.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.repositories.BoardGameTypeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardGameTypeService {
    private final BoardGameTypeRepository boardGameTypeRepository;
    private final ModelMapper mapper;

    public List<BoardGameTypeReadDto> findAll(){
        return boardGameTypeRepository.findAll().stream()
                .map(boardGameType -> mapper.map(boardGameType, BoardGameTypeReadDto.class))
                .toList();
    }

    public Optional<BoardGameTypeReadDto> findById(Long id){
        return Optional.of(boardGameTypeRepository.findById(id)
                        .map(boardGameType -> mapper.map(boardGameType, BoardGameTypeReadDto.class)))
                .orElse(null);
    }
}
