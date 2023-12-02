package org.kaoden.ws.homework.repository.entry;

import org.kaoden.ws.homework.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    List<Entry> findEntriesByName(String name);

    List<Entry> findEntriesByDescription(String description);

}
