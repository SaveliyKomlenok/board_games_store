package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.BoardGameCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.BoardGameReadDto;
import by.saveliykomlenok.boardgamesstore.dto.ManufacturerCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manufacturers")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @GetMapping
    public List<ManufacturerReadDto> findAll(){
        return manufacturerService.findAll();
    }

    @GetMapping("/{id}")
    public ManufacturerReadDto findById(@PathVariable("id") Long id){
        return manufacturerService.findById(id);
    }

    @PostMapping
    public ManufacturerReadDto create(@RequestBody ManufacturerCreateEditDto manufacturer){
        return manufacturerService.create(manufacturer);
    }

    @PutMapping("/{id}")
    public ManufacturerReadDto update(@PathVariable("id") Long id, @RequestBody ManufacturerCreateEditDto manufacturer){
        return manufacturerService.update(id, manufacturer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        manufacturerService.delete(id);
    }
}
