package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.service.BoardGameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-games")
public class BoardGameController {
    private final BoardGameService boardGameService;

    @GetMapping
    public List<BoardGameReadDto> findAll(){
        return boardGameService.findAll();
    }

    @GetMapping("/{id}")
    public BoardGameReadDto findById(@PathVariable("id") Long id){
        return boardGameService.findById(id)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public BoardGameReadDto create(@RequestBody BoardGameCreateEditDto boardGame){
        return boardGameService.create(boardGame);
    }

    @PutMapping("/{id}")
    public BoardGameReadDto update(@PathVariable("id") Long id, @RequestBody BoardGameCreateEditDto boardGame){
        return boardGameService.update(id, boardGame)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        if(!boardGameService.delete(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}