package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.service.AccessoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accessories")
public class AccessoryController {
    private final AccessoryService accessoryService;

    @GetMapping
    public List<AccessoryReadDto> findAll(){
        return accessoryService.findAll();
    }

    @GetMapping("/{id}")
    public AccessoryReadDto findById(@PathVariable("id") Long id){
        return accessoryService.findById(id);
    }

    @PostMapping
    public AccessoryReadDto create(@RequestBody AccessoryCreateEditDto accessoryDto){
        return accessoryService.create(accessoryDto);
    }

    @PutMapping("/{id}")
    public AccessoryReadDto update(@PathVariable("id") Long id, @RequestBody AccessoryCreateEditDto accessoryDto){
        return accessoryService.update(id, accessoryDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        accessoryService.delete(id);
    }
}
