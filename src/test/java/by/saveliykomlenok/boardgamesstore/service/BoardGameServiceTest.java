package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.constants.Constants;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameTypeReadDto;
import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.BoardGameType;
import by.saveliykomlenok.boardgamesstore.entity.Manufacturer;
import by.saveliykomlenok.boardgamesstore.repositoriy.BoardGameRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.boardgame.BoardGameIsExistsException;
import by.saveliykomlenok.boardgamesstore.util.exception.boardgame.BoardGameMissingException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class BoardGameServiceTest {

    @Mock
    private ModelMapper mapper;
    @Mock
    private BoardGameRepository boardGameRepository;
    @Mock
    private ManufacturerService manufacturerService;
    @Mock
    private BoardGameTypeService boardGameTypeService;
    @InjectMocks
    private BoardGameService boardGameService;

    @Test
    void findById_whenExists() {
        BoardGame boardGame = BoardGame.builder()
                .id(Constants.BOARD_GAME_ID)
                .build();
        BoardGameReadDto expected = BoardGameReadDto.builder()
                .id(Constants.BOARD_GAME_ID)
                .build();

        Mockito.when(boardGameRepository.findById(Constants.BOARD_GAME_ID)).thenReturn(Optional.of(boardGame));

        Mockito.when(mapper.map(boardGame, BoardGameReadDto.class)).thenReturn(expected);

        BoardGameReadDto actualResult = boardGameService.findById(Constants.BOARD_GAME_ID);

        assertEquals(expected, actualResult);
    }

    @Test
    void findById_whenNotExists() {
        BoardGame boardGame = BoardGame.builder()
                .id(Constants.BOARD_GAME_ID)
                .build();

        Mockito.when(boardGameRepository.findById(Constants.BOARD_GAME_ID)).thenReturn(Optional.empty());

        Mockito.when(mapper.map(boardGame, BoardGameReadDto.class)).thenReturn(null);

        assertThrows(BoardGameMissingException.class, () -> boardGameService.findById(Constants.BOARD_GAME_ID));
    }

    @Test
    void create_whenDtoNotNull_and_whenBoardGameNotExist() {
        BoardGameCreateEditDto boardGameCreateEditDto = BoardGameCreateEditDto.builder()
                .manufacturer(1L)
                .boardGameType(1L)
                .build();

        BoardGame boardGame = BoardGame.builder()
                .manufacturer(new Manufacturer())
                .boardGamesType(new BoardGameType())
                .build();

        BoardGameReadDto boardGameReadDto = BoardGameReadDto.builder()
                .id(1L)
                .manufacturer(new ManufacturerReadDto())
                .boardGameType(new BoardGameTypeReadDto())
                .build();

        Mockito.when(mapper.map(boardGameCreateEditDto, BoardGame.class)).thenReturn(boardGame);

        Mockito.when(manufacturerService.findById(boardGameCreateEditDto.getManufacturer())).thenReturn(new ManufacturerReadDto());
        Mockito.when(mapper.map(new ManufacturerReadDto(), Manufacturer.class)).thenReturn(new Manufacturer());

        Mockito.when(boardGameTypeService.findById(boardGameCreateEditDto.getBoardGameType())).thenReturn(new BoardGameTypeReadDto());
        Mockito.when(mapper.map(new BoardGameTypeReadDto(), BoardGameType.class)).thenReturn(new BoardGameType());

        Mockito.when(boardGameRepository.findBoardGameByNameAndPriceAndNumberOfPlayersAndAgeAndAmountAndManufacturerIdAndBoardGamesTypeId(
                "str",
                1d,
                1,
                1,
                1,
                1L,
                1L
        )).thenReturn(Optional.empty());

        Mockito.when(boardGameRepository.save(boardGame)).thenReturn(boardGame);

        boardGame.setId(1L);

        Mockito.when(mapper.map(boardGame, BoardGameReadDto.class)).thenReturn(boardGameReadDto);

        BoardGameReadDto actualResult = boardGameService.create(boardGameCreateEditDto);

        assertEquals(boardGameReadDto, actualResult);
        Mockito.verify(boardGameRepository, Mockito.times(1)).save(boardGame);
    }

    @Test
    void create_whenDtoNotNull_but_whenBoardGameExist() {
        BoardGameCreateEditDto boardGameCreateEditDto = BoardGameCreateEditDto.builder()
                .name("str")
                .price(1d)
                .numberOfPlayers(1)
                .age(1)
                .amount(1)
                .manufacturer(1L)
                .boardGameType(1L)
                .build();

        BoardGame boardGame = BoardGame.builder()
                .name("str")
                .price(1d)
                .numberOfPlayers(1)
                .age(1)
                .amount(1)
                .manufacturer(Manufacturer.builder()
                        .id(1L)
                        .build())
                .boardGamesType(BoardGameType.builder()
                        .id(1L)
                        .build())
                .build();

        Mockito.when(mapper.map(boardGameCreateEditDto, BoardGame.class)).thenReturn(boardGame);

        Mockito.when(manufacturerService.findById(boardGameCreateEditDto.getManufacturer())).thenReturn(new ManufacturerReadDto());
        Mockito.when(mapper.map(new ManufacturerReadDto(), Manufacturer.class)).thenReturn(new Manufacturer());

        Mockito.when(boardGameTypeService.findById(boardGameCreateEditDto.getBoardGameType())).thenReturn(new BoardGameTypeReadDto());
        Mockito.when(mapper.map(new BoardGameTypeReadDto(), BoardGameType.class)).thenReturn(new BoardGameType());

        Mockito.when(boardGameRepository.findBoardGameByNameAndPriceAndNumberOfPlayersAndAgeAndAmountAndManufacturerIdAndBoardGamesTypeId(
                "str",
                1d,
                1,
                1,
                1,
                1L,
                1L
        )).thenReturn(Optional.of(boardGame));

        assertThrows(BoardGameIsExistsException.class, () -> boardGameService.create(boardGameCreateEditDto));
        Mockito.verify(boardGameRepository, Mockito.times(0)).save(boardGame);
    }

    @Test
    void update_whenDtoNotNull_and_whenBoardGameNotExist() {
        Manufacturer manufacturer = Manufacturer.builder()
                .id(1L)
                .build();
        BoardGameType boardGameType = BoardGameType.builder()
                .id(1L)
                .build();
        BoardGameCreateEditDto boardGameCreateEditDto = BoardGameCreateEditDto.builder()
                .manufacturer(1L)
                .boardGameType(1L)
                .build();
        Optional<BoardGame> boardGame = Optional.of(BoardGame.builder()
                .id(1L)
                .manufacturer(manufacturer)
                .boardGamesType(boardGameType)
                .build());
        BoardGameReadDto boardGameReadDto = BoardGameReadDto.builder()
                .id(1L)
                .manufacturer(new ManufacturerReadDto())
                .boardGameType(new BoardGameTypeReadDto())
                .build();
        Mockito.when(boardGameRepository.findById(Constants.BOARD_GAME_ID)).thenReturn(boardGame);

        Mockito.when(boardGameRepository.findBoardGameByNameAndPriceAndNumberOfPlayersAndAgeAndAmountAndManufacturerIdAndBoardGamesTypeId(
                "str",
                1d,
                1,
                1,
                1,
                1L,
                1L
        )).thenReturn(Optional.empty());

        Mockito.when(manufacturerService.findById(boardGameCreateEditDto.getManufacturer())).thenReturn(new ManufacturerReadDto());
        Mockito.when(mapper.map(new ManufacturerReadDto(), Manufacturer.class)).thenReturn(manufacturer);
        Mockito.when(boardGameTypeService.findById(boardGameCreateEditDto.getBoardGameType())).thenReturn(new BoardGameTypeReadDto());
        Mockito.when(mapper.map(new BoardGameTypeReadDto(), BoardGameType.class)).thenReturn(boardGameType);

        Mockito.when(boardGameRepository.saveAndFlush(boardGame.get())).thenReturn(boardGame.get());

        Mockito.when(mapper.map(boardGame.get(), BoardGameReadDto.class)).thenReturn(boardGameReadDto);

        BoardGameReadDto actualResult = boardGameService.update(1L, boardGameCreateEditDto);

        assertEquals(boardGameReadDto, actualResult);

        Mockito.verify(mapper, Mockito.times(1)).map(boardGameCreateEditDto, boardGame.get());
        Mockito.verify(boardGameRepository, Mockito.times(1)).saveAndFlush(boardGame.get());
    }

    @Test
    void delete() {
        Optional<BoardGame> boardGame = Optional.of(BoardGame.builder()
                .id(1L)
                .build());

        Mockito.when(boardGameRepository.findById(Constants.BOARD_GAME_ID)).thenReturn(boardGame);

        boardGameService.delete(boardGame.get().getId());

        Mockito.verify(boardGameRepository, Mockito.times(1)).delete(boardGame.get());
    }

    @Test
    void delete_whenNotExist() {
        Mockito.when(boardGameRepository.findById(Constants.BOARD_GAME_ID)).thenReturn(Optional.empty());

        assertThrows(BoardGameMissingException.class, () -> boardGameService.delete(null));
        Mockito.verify(boardGameRepository, Mockito.times(0)).delete(any());
    }
}