package org.kaoden.ws.homework.repository.entry;

import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntryRepository extends BaseRepository<Entry, Long> {

    Page<Entry> findEntriesByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Entry> findEntriesByDescriptionContainingIgnoreCase(String description, Pageable pageable);

    Long getEntriesAverageValueIs5();

    Long getEntriesAverageValueEqualOrHigherThan4();

    Long getEntriesWithValuesLessThan4();

    Long getUnratedEntries();
}

