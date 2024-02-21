package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.CartBoardGames;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.repositoriy.CartBoardGameRepository;
import by.saveliykomlenok.boardgamesstore.util.exception.boardgame.AmountOfBoardGameExceededException;
import by.saveliykomlenok.boardgamesstore.util.exception.cart.CartBoardGameMissingException;
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
    private final ModelMapper mapper;

    public List<CartBoardGameReadDto> findAll(User user) {
        return cartBoardGameRepository.findCartBoardGamesByUserId(user.getId()).stream()
                .map(cartBoardGames -> mapper.map(cartBoardGames, CartBoardGameReadDto.class))
                .toList();
    }

    public CartBoardGameReadDto findById(Long id) {
        return cartBoardGameRepository.findById(id)
                .map(cartBoardGames -> mapper.map(cartBoardGames, CartBoardGameReadDto.class))
                .orElseThrow(() -> new CartBoardGameMissingException("Cart board game doesn't exist"));
    }

    @Transactional
    public CartBoardGameReadDto create(User user, CartBoardGameCreateEditDto cartBoardGameDto) {
        CartBoardGames cartBoardGames = Optional.of(cartBoardGameDto)
                .map(cartBoardGameCreateEditDto -> mapper.map(cartBoardGameDto, CartBoardGames.class))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));
        if (cartBoardGameRepository.existsCartBoardGamesByBoardGameIdAndUserId(cartBoardGameDto.getBoardGame(), user.getId())) {
            cartBoardGames = cartBoardGameRepository.findCartBoardGamesByBoardGameIdAndUserId(cartBoardGameDto.getBoardGame(), user.getId());
            cartBoardGames.setAmount(cartBoardGames.getAmount() + cartBoardGameDto.getAmount());
        }

        BoardGameReadDto boardGameReadDto = boardGameService.findById(cartBoardGameDto.getBoardGame());

        if (!isEnoughAmount(boardGameReadDto, cartBoardGames)) {
            throw new AmountOfBoardGameExceededException("Amount of board game to purchase has been exceeded");
        }

        cartBoardGames.setBoardGame(mapper.map(boardGameReadDto, BoardGame.class));
        cartBoardGames.setUser(user);

        return mapper.map(cartBoardGameRepository.saveAndFlush(cartBoardGames), CartBoardGameReadDto.class);
    }

    public boolean isEnoughAmount(BoardGameReadDto boardGameReadDto, CartBoardGames cartBoardGames) {
        return cartBoardGames.getAmount() <= boardGameReadDto.getAmount();
    }

    @Transactional
    public CartBoardGameReadDto update(User user, Long id, CartBoardGameCreateEditDto cartBoardGameDto) {
        CartBoardGames cartBoardGames = cartBoardGameRepository.findById(id)
                .orElseThrow(() -> new CartBoardGameMissingException("Cart board game doesn't exist"));

        mapper.map(cartBoardGameDto, cartBoardGames);

        cartBoardGames.setBoardGame(mapper.map(boardGameService.findById(cartBoardGameDto.getBoardGame()), BoardGame.class));
        cartBoardGames.setUser(user);

        return mapper.map(cartBoardGameRepository.saveAndFlush(cartBoardGames), CartBoardGameReadDto.class);
    }

    @Transactional
    public void delete(Long id) {
        cartBoardGameRepository.findById(id)
                .map(cartBoardGames -> {
                    cartBoardGameRepository.delete(cartBoardGames);
                    cartBoardGameRepository.flush();
                    return true;
                }).orElseThrow(() -> new CartBoardGameMissingException("Cart board game doesn't exist"));
    }

    @Transactional
    public void deleteCartByUserId(Long id) {
        cartBoardGameRepository.deleteAllByUserId(id);
    }
}
