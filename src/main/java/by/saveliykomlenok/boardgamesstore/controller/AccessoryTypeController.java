package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeReadDto;
import by.saveliykomlenok.boardgamesstore.service.AccessoryTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accessory-types")
@SecurityRequirement(name = "BearerAuth")
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

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping
    public AccessoryTypeReadDto create(@RequestBody AccessoryTypeCreateEditDto accessoryTypeDto){
        return accessoryTypeService.create(accessoryTypeDto);
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/{id}")
    public AccessoryTypeReadDto update(@PathVariable("id") Long id, @RequestBody AccessoryTypeCreateEditDto accessoryTypeDto){
        return accessoryTypeService.update(id, accessoryTypeDto);
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        accessoryTypeService.delete(id);
    }
}
