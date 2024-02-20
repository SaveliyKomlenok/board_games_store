package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import by.saveliykomlenok.boardgamesstore.repositoriy.BoardGameRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.boardgame.BoardGameIsExistsException;
import by.saveliykomlenok.boardgamesstore.util.exception.boardgame.BoardGameMissingException;
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

    public List<BoardGameReadDto> findAll() {
        return boardGameRepository.findAll().stream()
                .map(boardGame -> mapper.map(boardGame, BoardGameReadDto.class))
                .toList();
    }

    public BoardGameReadDto findById(Long id) {
        return boardGameRepository.findById(id)
                .map(boardGame -> mapper.map(boardGame, BoardGameReadDto.class))
                .orElseThrow(() ->  new BoardGameMissingException("Board game is out of stock"));
    }

    @Transactional
    public BoardGameReadDto create(BoardGameCreateEditDto boardDto) {
        BoardGame boardGame = Optional.of(boardDto)
                .map(boardGameCreateEditDto -> mapper.map(boardGameCreateEditDto, BoardGame.class))
                .orElseThrow();
        boardGame.setManufacturer(mapper.map(manufacturerService.findById(boardDto.getManufacturer()), Manufacturer.class));
        boardGame.setBoardGamesType(mapper.map(boardGameTypeService.findById(boardDto.getBoardGameType()), BoardGameType.class));

        if(validateCreateUpdate(boardDto)){
            throw new BoardGameIsExistsException("Board game is already exists");
        }

        return mapper.map(boardGameRepository.save(boardGame), BoardGameReadDto.class);
    }

    @Transactional
    public BoardGameReadDto update(Long id, BoardGameCreateEditDto boardDto) {
        BoardGame boardGame = boardGameRepository.findById(id)
                .orElseThrow(() ->  new BoardGameMissingException("Board game is out of stock"));

        if(validateCreateUpdate(id, boardDto)){
            throw new BoardGameIsExistsException("Board game is already exists");
        }

        mapper.map(boardDto, boardGame);

        boardGame.setManufacturer(mapper.map(manufacturerService.findById(boardDto.getManufacturer()), Manufacturer.class));
        boardGame.setBoardGamesType(mapper.map(boardGameTypeService.findById(boardDto.getBoardGameType()), BoardGameType.class));

        return mapper.map(boardGameRepository.saveAndFlush(boardGame), BoardGameReadDto.class);
    }

    private boolean validateCreateUpdate(Long id, BoardGameCreateEditDto boardGameDto){
        Optional<BoardGame> tempBoardGame = getBoardGame(boardGameDto);
        return tempBoardGame.isPresent() && !tempBoardGame.get().getId().equals(id);
    }

    private boolean validateCreateUpdate(BoardGameCreateEditDto boardGameDto){
        Optional<BoardGame> tempBoardGame = getBoardGame(boardGameDto);
        return tempBoardGame.isPresent();
    }

    private Optional<BoardGame> getBoardGame(BoardGameCreateEditDto boardGameDto) {
        return boardGameRepository.findBoardGameByNameAndPriceAndNumberOfPlayersAndAgeAndAmountAndManufacturerIdAndBoardGamesTypeId(
                boardGameDto.getName(),
                boardGameDto.getPrice(),
                boardGameDto.getNumberOfPlayers(),
                boardGameDto.getAge(),
                boardGameDto.getAmount(),
                boardGameDto.getManufacturer(),
                boardGameDto.getBoardGameType());
    }

    @Transactional
    public void delete(Long id) {
        boardGameRepository.findById(id)
                .map(boardGame -> {
                    boardGameRepository.delete(boardGame);
                    boardGameRepository.flush();
                    return true;
                }).orElseThrow(() ->  new BoardGameMissingException("Board game is out of stock"));
    }
}
