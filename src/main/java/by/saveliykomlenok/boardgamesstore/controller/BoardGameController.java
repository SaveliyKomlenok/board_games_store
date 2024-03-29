package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.service.BoardGameService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<BoardGameReadDto>> findAll(){
        return ResponseEntity.ok(boardGameService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGameReadDto> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(boardGameService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<BoardGameReadDto> create(@RequestBody BoardGameCreateEditDto boardGame){
        return ResponseEntity.ok(boardGameService.create(boardGame));
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/{id}")
    public ResponseEntity<BoardGameReadDto> update(@PathVariable("id") Long id, @RequestBody BoardGameCreateEditDto boardGame){
        return ResponseEntity.ok(boardGameService.update(id, boardGame));
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        boardGameService.delete(id);
        return ResponseEntity.ok("Board game removed");
    }
}
