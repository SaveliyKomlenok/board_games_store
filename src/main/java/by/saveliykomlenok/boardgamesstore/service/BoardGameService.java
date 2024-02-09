package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import by.saveliykomlenok.boardgamesstore.repositories.BoardGameRepository;
import by.saveliykomlenok.boardgamesstore.repositories.BoardGameTypeRepository;
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
public class BoardGameService {
    private final BoardGameRepository boardGameRepository;
    private final ManufacturerService manufacturerService;
    private final BoardGameTypeService boardGameTypeService;
    private final ModelMapper mapper;

    public List<BoardGameReadDto> findAll(){
        return boardGameRepository.findAll().stream()
                .map(entity -> mapper.map(entity, BoardGameReadDto.class))
                .toList();
    }

    public Optional<BoardGameReadDto> findById(Long id){
        return Optional.of(boardGameRepository.findById(id)
                .map(entity -> mapper.map(entity, BoardGameReadDto.class)))
                .orElse(null);
    }

    @Transactional
    public BoardGameReadDto create(BoardGameCreateEditDto boardDto){
        BoardGame boardGame = Optional.of(boardDto)
                .map(entity -> mapper.map(entity, BoardGame.class)).orElseThrow();
        boardGame.setManufacturer(mapper.map(manufacturerService.findById(boardDto.getManufacturer()).orElse(null), Manufacturer.class));
        boardGame.setBoardGamesType(mapper.map(boardGameTypeService.findById(boardDto.getBoardGameType()).orElse(null), BoardGameType.class));

        boardGameRepository.save(boardGame);

        return mapper.map(boardGame, BoardGameReadDto.class);
    }

    @Transactional
    public Optional<BoardGameReadDto> update(Long id, BoardGameCreateEditDto boardDto){
        BoardGame boardGame = boardGameRepository.findById(id).orElseThrow();
        boardGame.setManufacturer(mapper.map(manufacturerService.findById(boardDto.getManufacturer()).orElse(null), Manufacturer.class));
        boardGame.setBoardGamesType(mapper.map(boardGameTypeService.findById(boardDto.getBoardGameType()).orElse(null), BoardGameType.class));

        boardGameRepository.saveAndFlush(boardGame);

        return Optional.of(mapper.map(boardGame, BoardGameReadDto.class));
    }

    @Transactional
    public boolean delete(Long id){
        return boardGameRepository.findById(id)
                .map(entity -> {
                    boardGameRepository.delete(entity);
                    boardGameRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
