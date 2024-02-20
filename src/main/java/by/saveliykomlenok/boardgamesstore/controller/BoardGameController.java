package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.service.BoardGameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-games")
@SecurityRequirement(name = "BearerAuth")
public class BoardGameController {
    private final BoardGameService boardGameService;

    @GetMapping
    public List<BoardGameReadDto> findAll(){
        return boardGameService.findAll();
    }

    @GetMapping("/{id}")
    public BoardGameReadDto findById(@PathVariable("id") Long id){
        return boardGameService.findById(id);
    }

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping
    public BoardGameReadDto create(@RequestBody BoardGameCreateEditDto boardGame){
        return boardGameService.create(boardGame);
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/{id}")
    public BoardGameReadDto update(@PathVariable("id") Long id, @RequestBody BoardGameCreateEditDto boardGame){
        return boardGameService.update(id, boardGame);
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        boardGameService.delete(id);
    }
}
