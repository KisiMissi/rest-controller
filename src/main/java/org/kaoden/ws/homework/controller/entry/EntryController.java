package org.kaoden.ws.homework.controller.entry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.kaoden.ws.homework.controller.entry.dto.CreateEntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.EntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.controller.entry.mapper.EntryMapper;
import org.kaoden.ws.homework.service.entry.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("entries")
@Tag(name="Entry Service", description = "Handle requests")
public class EntryController {

    private final EntryService service;
    private final EntryMapper mapper;

    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(description = "Return created Entry")
    public EntryDTO create(@Valid @RequestBody CreateEntryDTO entryDTO) {
        return mapper.toDTO(service.create(mapper.toModel(entryDTO)));
    }

    @GetMapping("all")
    @Operation(description = "Return all entries from the storage or entries by name")
    public List<EntryDTO> getAll(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String description) {
        return mapper.toDTOList(service.getAll(name, description));
    }

    @GetMapping("{id}")
    @Operation(description = "Search Entry by ID")
    public EntryDTO searchById(@PathVariable Long id) {
        return mapper.toDTO(service.getExisting(id));
    }

    @PostMapping("{id}/update")
    @Operation(description = "Update Entry by ID")
    public EntryDTO updateEntry(@PathVariable Long id,
                                @Valid @RequestBody UpdateEntryDTO entry) {
        return mapper.toDTO(service.update(id, mapper.toModel(entry)));
    }

    @PostMapping("{id}/delete")
    @Operation(description = "Delete Entry by ID")
    public void deleteEntry(@PathVariable Long id) {
        service.delete(id);
    }
}
