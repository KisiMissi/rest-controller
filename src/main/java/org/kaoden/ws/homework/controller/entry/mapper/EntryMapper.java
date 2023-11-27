package org.kaoden.ws.homework.controller.entry.mapper;

import org.kaoden.ws.homework.controller.entry.dto.CreateEntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.EntryDTO;
import org.kaoden.ws.homework.controller.entry.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntryMapper {

    EntryDTO toDTO(Entry entry);

    List<EntryDTO> toDTOList(List<Entry> entries);

    CreateEntryArgument toModel(CreateEntryDTO entryDto);

    UpdateEntryArgument toModel(UpdateEntryDTO entryDTO);

}
