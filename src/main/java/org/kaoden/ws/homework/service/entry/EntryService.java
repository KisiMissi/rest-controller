package org.kaoden.ws.homework.service.entry;

import org.kaoden.ws.homework.model.Entry;

import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;

import java.util.List;

public interface EntryService {

    Entry create(CreateEntryArgument entry);

    Entry getExisting(Long id);

    List<Entry> getAll(String name, String description);

    Entry update(Long id, UpdateEntryArgument entry);

    void delete(Long id);

    Boolean exists(Long id);

}
