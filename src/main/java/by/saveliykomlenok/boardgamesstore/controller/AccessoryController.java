package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.accessory.AccessoryReadDto;
import by.saveliykomlenok.boardgamesstore.service.AccessoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accessories")
@SecurityRequirement(name = "BearerAuth")
public class AccessoryController {
    private final AccessoryService accessoryService;

    @GetMapping
    public ResponseEntity<List<AccessoryReadDto>> findAll(){
        return ResponseEntity.ok(accessoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccessoryReadDto> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(accessoryService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<AccessoryReadDto> create(@RequestBody AccessoryCreateEditDto accessoryDto){
        return ResponseEntity.ok(accessoryService.create(accessoryDto));
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/{id}")
    public ResponseEntity<AccessoryReadDto> update(@PathVariable("id") Long id, @RequestBody AccessoryCreateEditDto accessoryDto){
        return ResponseEntity.ok(accessoryService.update(id, accessoryDto));
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        accessoryService.delete(id);
        return ResponseEntity.ok("Accessory removed");
    }
}
