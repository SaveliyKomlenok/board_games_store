package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.BoardGameTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.BoardGameTypeReadDto;
import by.saveliykomlenok.boardgamesstore.dto.ManufacturerCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.service.BoardGameTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-game-types")
public class BoardGameTypeController {
    private final BoardGameTypeService boardGameTypeService;

    @GetMapping
    public List<BoardGameTypeReadDto> findAll(){
        return boardGameTypeService.findAll();
    }

    @GetMapping("/{id}")
    public BoardGameTypeReadDto findById(@PathVariable("id") Long id){
        return boardGameTypeService.findById(id);
    }

    @PostMapping
    public BoardGameTypeReadDto create(@RequestBody BoardGameTypeCreateEditDto boardGameType){
        return boardGameTypeService.create(boardGameType);
    }

    @PutMapping("/{id}")
    public BoardGameTypeReadDto update(@PathVariable("id") Long id, @RequestBody BoardGameTypeCreateEditDto boardGameType){
        return boardGameTypeService.update(id, boardGameType);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        boardGameTypeService.delete(id);
    }
}
