package org.kaoden.ws.homework.service;

import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.service.argument.EntryArgument;

import java.util.List;

public interface EntryService {

    Entry create(EntryArgument entry);

    Entry getExisting(Long id);

    List<Entry> search(String searchText);

    List<Entry> getAll();

    Entry update(Long id, EntryArgument entry);

    void delete(Long id);

}
