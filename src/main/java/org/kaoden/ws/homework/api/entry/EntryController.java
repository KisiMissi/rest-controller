package org.kaoden.ws.homework.api.entry;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.api.entry.dto.CreateEntryDTO;
import org.kaoden.ws.homework.api.entry.dto.EntryDTO;
import org.kaoden.ws.homework.api.entry.dto.SearchEntryDTO;
import org.kaoden.ws.homework.api.entry.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.api.entry.mapper.EntryMapper;
import org.kaoden.ws.homework.service.entry.EntryService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("entries")
@Tag(name="Entry Service", description = "Handle requests")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class EntryController {

    EntryService service;
    EntryMapper mapper;

    @PostMapping("create")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(description = "Return created Entry")
    public EntryDTO create(@Valid @RequestBody CreateEntryDTO entryDTO) {
        return mapper.toDTO(service.create(mapper.toModel(entryDTO)));
    }

    @GetMapping("all")
    @Operation(description = "Return all entries from the storage or entries by name")
    public List<EntryDTO> getAll(SearchEntryDTO searchDto,
                                 @PageableDefault(sort = {"name"}) Pageable pageable) {
        return mapper.toDTOList(service.getAll(mapper.toModel(searchDto), pageable));
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
