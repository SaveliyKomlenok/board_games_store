package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.CartBoardGames;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.repositoriy.CartBoardGameRepository;
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
public class CartBoardGameService{
    private final CartBoardGameRepository cartBoardGameRepository;
    private final BoardGameService boardGameService;
    private final UserService userService;
    private final ModelMapper mapper;

    public List<CartBoardGameReadDto> findAll(Long id) {
//        return cartBoardGameRepository.findAll().stream()
//                .map(cartBoardGames -> mapper.map(cartBoardGames, CartBoardGameReadDto.class))
//                .toList();
        return cartBoardGameRepository.findCartBoardGamesByUserId(id).stream()
                .map(cartBoardGames -> mapper.map(cartBoardGames, CartBoardGameReadDto.class))
                .toList();
    }

    public CartBoardGameReadDto findById(Long id) {
        return cartBoardGameRepository.findById(id)
                .map(cartBoardGames -> mapper.map(cartBoardGames, CartBoardGameReadDto.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public CartBoardGameReadDto create(User user, CartBoardGameCreateEditDto cartBoardGameDto) {
        CartBoardGames cartBoardGames = Optional.of(cartBoardGameDto)
                .map(cartBoardGameCreateEditDto -> mapper.map(cartBoardGameDto, CartBoardGames.class))
                .orElseThrow();
        if (cartBoardGameRepository.existsCartBoardGamesByBoardGameIdAndUserId(cartBoardGameDto.getBoardGame(), cartBoardGameDto.getUser())) {
            cartBoardGames = cartBoardGameRepository.findCartBoardGamesByBoardGameIdAndUserId(cartBoardGameDto.getBoardGame(), cartBoardGameDto.getUser());
            cartBoardGames.setAmount(cartBoardGames.getAmount() + cartBoardGameDto.getAmount());
        }

        BoardGameReadDto boardGameReadDto = boardGameService.findById(cartBoardGameDto.getBoardGame());

        if (isEnoughAmount(boardGameReadDto, cartBoardGameDto)) {
            boardGameReadDto = removeAmountOfBoardGame(cartBoardGameDto, boardGameReadDto);
        } else throw new ResponseStatusException(HttpStatus.CONFLICT);

        cartBoardGames.setBoardGame(mapper.map(boardGameReadDto, BoardGame.class));
        cartBoardGames.setUser(mapper.map(userService.findById(cartBoardGameDto.getUser()), User.class));
        //cartBoardGames.setUser(user); Реализовать после JWT

        cartBoardGameRepository.saveAndFlush(cartBoardGames);
        return mapper.map(cartBoardGames, CartBoardGameReadDto.class);
    }

    private BoardGameReadDto removeAmountOfBoardGame(CartBoardGameCreateEditDto cartBoardGameCreateEditDto, BoardGameReadDto boardGameReadDto) {
        BoardGameCreateEditDto boardGameCreateEditDto = BoardGameCreateEditDto.builder()
                .name(boardGameReadDto.getName())
                .price(boardGameReadDto.getPrice())
                .numberOfPlayers(boardGameReadDto.getNumberOfPlayers())
                .age(boardGameReadDto.getAge())
                .amount(boardGameReadDto.getAmount() - cartBoardGameCreateEditDto.getAmount())
                .manufacturer(boardGameReadDto.getManufacturer().getId())
                .boardGameType(boardGameReadDto.getBoardGameType().getId())
                .build();
        return boardGameService.update(boardGameReadDto.getId(), boardGameCreateEditDto);
    }

    public boolean isEnoughAmount(BoardGameReadDto boardGameReadDto, CartBoardGameCreateEditDto cartBoardGameDto) {
        return (boardGameReadDto.getAmount() - cartBoardGameDto.getAmount()) >= 0;
    }

    @Transactional
    public CartBoardGameReadDto update(Long id, CartBoardGameCreateEditDto cartBoardGameDto) {
        CartBoardGames cartBoardGames = cartBoardGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        mapper.map(cartBoardGameDto, cartBoardGames);

        cartBoardGames.setBoardGame(mapper.map(boardGameService.findById(cartBoardGameDto.getBoardGame()), BoardGame.class));
        cartBoardGames.setUser(mapper.map(userService.findById(cartBoardGameDto.getUser()), User.class));

        cartBoardGameRepository.saveAndFlush(cartBoardGames);
        return mapper.map(cartBoardGames, CartBoardGameReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        rollbackAmountOfBoardGames(id);
        cartBoardGameRepository.findById(id)
                .map(cartBoardGames -> {
                    cartBoardGameRepository.delete(cartBoardGames);
                    cartBoardGameRepository.flush();
                    return true;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private void rollbackAmountOfBoardGames(Long id) {
        CartBoardGames cartBoardGames = cartBoardGameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        amountRollback(cartBoardGames);
    }

    public void amountRollback(CartBoardGames cartBoardGames) {
        rollback(cartBoardGames.getBoardGame(), cartBoardGames.getAmount(), boardGameService);
    }

    static void rollback(BoardGame boardGame, int amount, BoardGameService boardGameService) {
        BoardGameCreateEditDto boardGameCreateEditDto = BoardGameCreateEditDto.builder()
                .name(boardGame.getName())
                .price(boardGame.getPrice())
                .numberOfPlayers(boardGame.getNumberOfPlayers())
                .age(boardGame.getAge())
                .amount(boardGame.getAmount() + amount)
                .manufacturer(boardGame.getManufacturer().getId())
                .boardGameType(boardGame.getBoardGamesType().getId())
                .build();
        boardGameService.update(boardGame.getId(), boardGameCreateEditDto);
    }

    @Transactional
    public void deleteCartByUserId(Long id) {
        cartBoardGameRepository.deleteAllByUserId(id);
    }
}
