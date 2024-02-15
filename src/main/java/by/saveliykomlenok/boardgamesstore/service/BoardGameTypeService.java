package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameTypeReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import by.saveliykomlenok.boardgamesstore.repositoriy.BoardGameTypeRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.boardgame.BoardGameTypeMissingException;
import by.saveliykomlenok.boardgamesstore.util.exception.boardgame.BoardTypeIsExistsException;
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
public class BoardGameTypeService {
    private final BoardGameTypeRepository boardGameTypeRepository;
    private final ModelMapper mapper;

    public List<BoardGameTypeReadDto> findAll() {
        return boardGameTypeRepository.findAll().stream()
                .map(boardGameType -> mapper.map(boardGameType, BoardGameTypeReadDto.class))
                .toList();
    }

    public BoardGameTypeReadDto findById(Long id) {
        return boardGameTypeRepository.findById(id)
                .map(boardGameType -> mapper.map(boardGameType, BoardGameTypeReadDto.class))
                .orElseThrow(() -> new BoardGameTypeMissingException("Board game type doesn't exist"));
    }

    @Transactional
    public BoardGameTypeReadDto create(BoardGameTypeCreateEditDto boardGameTypeDto) {
        BoardGameType boardGameType = Optional.of(boardGameTypeDto)
                .map(boardGameTypeCreateEditDto -> mapper.map(boardGameTypeDto, BoardGameType.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
        if (validateCreateUpdate(boardGameTypeDto)) {
            throw new BoardTypeIsExistsException("Board game type is already exists");
        }
        boardGameTypeRepository.save(boardGameType);
        return mapper.map(boardGameType, BoardGameTypeReadDto.class);
    }

    @Transactional
    public BoardGameTypeReadDto update(Long id, BoardGameTypeCreateEditDto boardGameTypeDto) {
        BoardGameType boardGameType = boardGameTypeRepository.findById(id)
                .orElseThrow(() -> new BoardGameTypeMissingException("Board game type doesn't exist"));
        if (validateCreateUpdate(id, boardGameTypeDto)) {
            throw new BoardTypeIsExistsException("Board game type is already exists");
        }
        mapper.map(boardGameTypeDto, boardGameType);
        boardGameTypeRepository.saveAndFlush(boardGameType);
        return mapper.map(boardGameType, BoardGameTypeReadDto.class);
    }

    private boolean validateCreateUpdate(Long id, BoardGameTypeCreateEditDto boardGameTypeDto) {
        Optional<BoardGameType> tempBoardGameType = boardGameTypeRepository.findBoardGameTypeByName(boardGameTypeDto.getName());
        return tempBoardGameType.isPresent() && !tempBoardGameType.get().getId().equals(id);
    }

    private boolean validateCreateUpdate(BoardGameTypeCreateEditDto boardGameTypeDto) {
        Optional<BoardGameType> tempBoardGameType = boardGameTypeRepository.findBoardGameTypeByName(boardGameTypeDto.getName());
        return tempBoardGameType.isPresent();
    }

    @Transactional
    public void delete(Long id) {
        boardGameTypeRepository.findById(id)
                .map(entity -> {
                    boardGameTypeRepository.delete(entity);
                    boardGameTypeRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new BoardGameTypeMissingException("Board game type doesn't exist"));
    }
}
