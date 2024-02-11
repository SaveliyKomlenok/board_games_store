package by.saveliykomlenok.boardgamesstore.service;

import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.cart.CartBoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.entity.BoardGame;
import by.saveliykomlenok.boardgamesstore.entity.CartBoardGames;
import by.saveliykomlenok.boardgamesstore.entity.User;
import by.saveliykomlenok.boardgamesstore.repositories.CartBoardGameRepository;
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
public class CartBoardGameService {
    private final CartBoardGameRepository cartBoardGameRepository;
    private final BoardGameService boardGameService;
    private final UserService userService;
    private final ModelMapper mapper;

    public List<CartBoardGameReadDto> findAll(User user) {
        return cartBoardGameRepository.findAll().stream()
                .map(cartBoardGames -> mapper.map(cartBoardGames, CartBoardGameReadDto.class))
                .toList();
//        return cartBoardGameRepository.findCartBoardGamesByUserId(user.getId()).stream()
//                .map(cartBoardGames -> mapper.map(cartBoardGames, CartBoardGameReadDto.class))
//                .toList();
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
        cartBoardGames.setBoardGame(mapper.map(boardGameService.findById(cartBoardGameDto.getBoardGame()), BoardGame.class));
        cartBoardGames.setUser(mapper.map(userService.findById(cartBoardGameDto.getUser()), User.class));
        //cartBoardGames.setUser(user); Реализовать после JWT

        cartBoardGameRepository.saveAndFlush(cartBoardGames);
        return mapper.map(cartBoardGames, CartBoardGameReadDto.class);
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
        cartBoardGameRepository.findById(id)
                .map(cartBoardGames -> {
                    cartBoardGameRepository.delete(cartBoardGames);
                    cartBoardGameRepository.flush();
                    return true;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
