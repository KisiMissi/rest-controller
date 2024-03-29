package org.kaoden.ws.homework.api.entry.mapper;

import org.kaoden.ws.homework.api.entry.dto.CreateEntryDTO;
import org.kaoden.ws.homework.api.entry.dto.EntryDTO;
import org.kaoden.ws.homework.api.entry.dto.SearchEntryDTO;
import org.kaoden.ws.homework.api.entry.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.SearchEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntryMapper {

    EntryDTO toDTO(Entry entry);

    List<EntryDTO> toDTOList(Page<Entry> entries);

    CreateEntryArgument toModel(CreateEntryDTO entryDto);

    UpdateEntryArgument toModel(UpdateEntryDTO entryDTO);

    SearchEntryArgument toModel(SearchEntryDTO searchDTO);

}
