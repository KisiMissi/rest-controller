package org.kaoden.ws.homework.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.kaoden.ws.homework.controller.dto.EntryDTO;
import org.kaoden.ws.homework.controller.mapper.EntryMapper;
import org.kaoden.ws.homework.service.EntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("entries-controller")
@Tag(name="Entry Controller", description = "Handle requests")
public class EntryController {

    private final EntryService service;
    private final EntryMapper mapper;

    @PostMapping("creat")
    public void creat(@RequestBody EntryDTO entryDTO) {
        service.creat(mapper.toModel(entryDTO));
    }

    @GetMapping("all")
    public List<EntryDTO> getAll() {
        return mapper.toDTOList(service.getAll());
    }

    @GetMapping("search-id/{id}")
    public EntryDTO searchById(@PathVariable Long id) {
        return mapper.toDTO(service.getExisting(id));
    }

    @GetMapping("search-name/{name}")
    public List<EntryDTO> searchByName(@PathVariable String name) {
        return mapper.toDTOList(service.search(name));
    }

    @PostMapping("{id}/update")
    public void updateEntry(@PathVariable Long id,
                             @RequestBody EntryDTO entry) {
        service.update(id, mapper.toModel(entry));
    }

    @PostMapping("{id}/delete")
    public void deleteEntry(@PathVariable Long id) {
        service.delete(id);
    }
}
