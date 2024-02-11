package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameTypeReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import by.saveliykomlenok.boardgamesstore.repositories.BoardGameTypeRepository;
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public BoardGameTypeReadDto create(BoardGameTypeCreateEditDto boardGameTypeDto) {
        return Optional.of(boardGameTypeDto)
                .map(boardGameTypeCreateEditDto -> mapper.map(boardGameTypeDto, BoardGameType.class))
                .map(boardGameTypeRepository::save)
                .map(boardGameType -> mapper.map(boardGameType, BoardGameTypeReadDto.class))
                .orElse(null);
    }

    @Transactional
    public BoardGameTypeReadDto update(Long id, BoardGameTypeCreateEditDto boardGameTypeDto) {
        BoardGameType boardGameType = boardGameTypeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapper.map(boardGameTypeDto, boardGameType);
        boardGameTypeRepository.saveAndFlush(boardGameType);
        return mapper.map(boardGameType, BoardGameTypeReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        boardGameTypeRepository.findById(id)
                .map(entity -> {
                    boardGameTypeRepository.delete(entity);
                    boardGameTypeRepository.flush();
                    return true;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
