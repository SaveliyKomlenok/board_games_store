package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import by.saveliykomlenok.boardgamesstore.repositories.BoardGameRepository;
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
public class BoardGameService {
    private final BoardGameRepository boardGameRepository;
    private final ManufacturerService manufacturerService;
    private final BoardGameTypeService boardGameTypeService;
    private final ModelMapper mapper;

    public List<BoardGameReadDto> findAll() {
        return boardGameRepository.findAll().stream()
                .map(boardGame -> mapper.map(boardGame, BoardGameReadDto.class))
                .toList();
    }

    public BoardGameReadDto findById(Long id) {
        return boardGameRepository.findById(id)
                .map(boardGame -> mapper.map(boardGame, BoardGameReadDto.class))
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public BoardGameReadDto create(BoardGameCreateEditDto boardDto) {
        BoardGame boardGame = Optional.of(boardDto)
                .map(boardGameCreateEditDto -> mapper.map(boardGameCreateEditDto, BoardGame.class))
                .orElseThrow();
        boardGame.setManufacturer(mapper.map(manufacturerService.findById(boardDto.getManufacturer()), Manufacturer.class));
        boardGame.setBoardGamesType(mapper.map(boardGameTypeService.findById(boardDto.getBoardGameType()), BoardGameType.class));

        boardGameRepository.save(boardGame);

        return mapper.map(boardGame, BoardGameReadDto.class);
    }

    @Transactional
    public BoardGameReadDto update(Long id, BoardGameCreateEditDto boardDto) {
        BoardGame boardGame = boardGameRepository.findById(id)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));

        mapper.map(boardDto, boardGame);

        boardGame.setManufacturer(mapper.map(manufacturerService.findById(boardDto.getManufacturer()), Manufacturer.class));
        boardGame.setBoardGamesType(mapper.map(boardGameTypeService.findById(boardDto.getBoardGameType()), BoardGameType.class));

        boardGameRepository.saveAndFlush(boardGame);

        return mapper.map(boardGame, BoardGameReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        boardGameRepository.findById(id)
                .map(boardGame -> {
                    boardGameRepository.delete(boardGame);
                    boardGameRepository.flush();
                    return true;
                }).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
