package org.kaoden.ws.homework.service;

import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.service.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.argument.UpdateEntryArgument;

import java.util.List;

public interface EntryService {

    Entry create(CreateEntryArgument entry);

    Entry getExisting(Long id);

    List<Entry> getAll(String searchText);

    Entry update(Long id, UpdateEntryArgument entry);

    void delete(Long id);

}
