package by.saveliykomlenok.boardgamesstore.controller;

import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerCreateEditDto;
import by.saveliykomlenok.boardgamesstore.dto.manufacturer.ManufacturerReadDto;
import by.saveliykomlenok.boardgamesstore.service.ManufacturerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/manufacturers")
@SecurityRequirement(name = "BearerAuth")
public class ManufacturerController {
    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerReadDto>> findAll(){
        return ResponseEntity.ok(manufacturerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerReadDto> findById(@PathVariable("id") Long id){
        return ResponseEntity.ok(manufacturerService.findById(id));
    }

    @PreAuthorize("hasAnyAuthority('admin:create')")
    @PostMapping
    public ResponseEntity<ManufacturerReadDto> create(@RequestBody ManufacturerCreateEditDto manufacturer){
        return ResponseEntity.ok(manufacturerService.create(manufacturer));
    }

    @PreAuthorize("hasAnyAuthority('admin:update')")
    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerReadDto> update(@PathVariable("id") Long id, @RequestBody ManufacturerCreateEditDto manufacturer){
        return ResponseEntity.ok(manufacturerService.update(id, manufacturer));
    }

    @PreAuthorize("hasAnyAuthority('admin:delete')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        manufacturerService.delete(id);
        return ResponseEntity.ok("Manufacturer removed");
    }
}
