package org.kaoden.ws.homework.repository.entry;

import org.kaoden.ws.homework.model.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

    Page<Entry> findEntriesByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Entry> findEntriesByDescriptionContainingIgnoreCase(String description, Pageable pageable);

}
