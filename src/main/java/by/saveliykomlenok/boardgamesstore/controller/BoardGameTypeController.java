package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.boardgame.BoardGameTypeReadDto;
import by.saveliykomlenok.boardgamesstore.service.BoardGameTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board-game-types")
@SecurityRequirement(name = "BearerAuth")
public class BoardGameTypeController {
    private final BoardGameTypeService boardGameTypeService;

    @GetMapping
    public ResponseEntity<List<BoardGameTypeReadDto>> findAll(){
        return ResponseEntity.ok(boardGameTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardGameTypeReadDto> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(boardGameTypeService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<BoardGameTypeReadDto> create(@RequestBody BoardGameTypeCreateEditDto boardGameType){
        return ResponseEntity.ok(boardGameTypeService.create(boardGameType));
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/{id}")
    public ResponseEntity<BoardGameTypeReadDto> update(@PathVariable("id") Long id, @RequestBody BoardGameTypeCreateEditDto boardGameType){
        return ResponseEntity.ok(boardGameTypeService.update(id, boardGameType));
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        boardGameTypeService.delete(id);
        return ResponseEntity.ok("Board game type removed");
    }
}
