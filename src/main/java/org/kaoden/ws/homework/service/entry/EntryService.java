package org.kaoden.ws.homework.service.entry;

import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.SearchEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntryService {

    Entry create(CreateEntryArgument entry);

    Entry getExisting(Long id);

    Page<Entry> getAll(SearchEntryArgument searchArgument, Pageable pageable);

    Entry update(Long id, UpdateEntryArgument entry);

    void delete(Long id);

    Boolean exists(Long id);

}
