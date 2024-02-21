package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryTypeReadDto;
import by.saveliykomlenok.boardgamesstore.service.AccessoryTypeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AccessoryTypeReadDto>> findAll(){
        return ResponseEntity.ok(accessoryTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessoryTypeReadDto> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(accessoryTypeService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<AccessoryTypeReadDto> create(@RequestBody AccessoryTypeCreateEditDto accessoryTypeDto){
        return ResponseEntity.ok(accessoryTypeService.create(accessoryTypeDto));
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/{id}")
    public ResponseEntity<AccessoryTypeReadDto> update(@PathVariable("id") Long id, @RequestBody AccessoryTypeCreateEditDto accessoryTypeDto){
        return ResponseEntity.ok(accessoryTypeService.update(id, accessoryTypeDto));
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        accessoryTypeService.delete(id);
        return ResponseEntity.ok("Accessory type removed");
    }
}
