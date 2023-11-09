package org.kaoden.ws.homework.service;

import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.service.argument.EntryArgument;

import java.util.List;

public interface EntryService {

    void creat(EntryArgument entry);

    Entry getExisting(Long id);

    List<Entry> search(String searchText);

    List<Entry> getAll();

    void update(Long id, EntryArgument entry);

    void delete(Long id);

}
