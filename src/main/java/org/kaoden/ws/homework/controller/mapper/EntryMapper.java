package org.kaoden.ws.homework.controller.mapper;

import org.kaoden.ws.homework.controller.dto.CreateEntryDTO;
import org.kaoden.ws.homework.controller.dto.EntryDTO;
import org.kaoden.ws.homework.controller.dto.UpdateEntryDTO;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.service.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.argument.UpdateEntryArgument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EntryMapper {

    EntryDTO toDTO(Entry entry);

    List<EntryDTO> toDTOList(List<Entry> entries);

    CreateEntryArgument toModel(CreateEntryDTO entryDto);

    UpdateEntryArgument toModel(UpdateEntryDTO entryDTO);

}
