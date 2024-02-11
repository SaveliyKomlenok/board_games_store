package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeReadDto;
import by.saveliykomlenok.boardgamesstore.service.AccessoryTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accessory-types")
public class AccessoryTypeController {
    private final AccessoryTypeService accessoryTypeService;

    @GetMapping
    public List<AccessoryTypeReadDto> findAll(){
        return accessoryTypeService.findAll();
    }

    @GetMapping("/{id}")
    public AccessoryTypeReadDto findById(@PathVariable("id") Long id){
        return accessoryTypeService.findById(id);
    }

    @PostMapping
    public AccessoryTypeReadDto create(@RequestBody AccessoryTypeCreateEditDto accessoryTypeDto){
        return accessoryTypeService.create(accessoryTypeDto);
    }

    @PutMapping("/{id}")
    public AccessoryTypeReadDto update(@PathVariable("id") Long id, @RequestBody AccessoryTypeCreateEditDto accessoryTypeDto){
        return accessoryTypeService.update(id, accessoryTypeDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        accessoryTypeService.delete(id);
    }
}
