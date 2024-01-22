package org.kaoden.ws.homework.service.entry;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.annotation.Statistics;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.SearchEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.kaoden.ws.homework.service.entry.statistics.EntriesStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE)
public class EntryServiceImpl implements EntryService {

    private final EntryRepository repository;

    @Override
    @Statistics
    @Transactional
    public Entry create(CreateEntryArgument entry) {
        return repository.save(Entry.builder()
                                    .name(entry.getName())
                                    .description(entry.getDescription())
                                    .links((entry.getLinks()))
                                    .build());
    }

    @Override
    @Transactional
    public Entry getExisting(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new NotFoundException("There is no entry with this ID: " + id));
    }

    @Override
    public Page<Entry> getAll(SearchEntryArgument searchArgument, Pageable pageable) {
        String name = searchArgument.getName();
        String description = searchArgument.getDescription();

        if (name != null) {
            return repository.findEntriesByNameContainingIgnoreCase(name, pageable);
        } else if (description != null) {
            return repository.findEntriesByDescriptionContainingIgnoreCase(description, pageable);
        } else {
            return repository.findAll(pageable);
        }
    }

    @Override
    @Transactional
    public Entry update(Long id, UpdateEntryArgument entry) {
        Entry updateEntry = getExisting(id);

        updateEntry.setName(entry.getName());
        updateEntry.setDescription(entry.getDescription());
        updateEntry.setLinks(entry.getLinks());

        return repository.save(updateEntry);
    }

    @Override
    @Statistics
    @Transactional
    public void delete(Long id) {
        if (!exists(id)) {
            throw new NotFoundException("Impossible delete entry with this ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean exists(Long id) {
        return repository.existsById(id);
    }

    @Transactional(readOnly = true)
    public EntriesStatistics getEntriesStatistics() {

        Long totalEntries = repository.count();

        Long entriesAvgValueIs5 = repository.getEntriesAverageValueIs5();
        Double percentageAvgValueIs5 = ((double) entriesAvgValueIs5 / totalEntries) * 100;

        Long entriesAvgValueGOE4 = repository.getEntriesAverageValueEqualOrHigherThan4();
        Double percentageOfEntriesAverageValueEqualOrHigherThan4 = ((double) entriesAvgValueGOE4 / totalEntries) * 100;

        Long entriesWithValuesLessThan4 = repository.getEntriesWithValuesLessThan4();
        Double percentageOfEntriesWithValuesLessThan4 = ((double) entriesWithValuesLessThan4 / totalEntries) * 100;

        return EntriesStatistics.builder()
                                .totalEntries(repository.count())
                                .entriesAverageValueIs5(entriesAvgValueIs5)
                                .percentageOfEntriesAverageValueIs5(percentageAvgValueIs5)
                                .entriesAverageValueEqualOrHigherThan4(entriesAvgValueGOE4)
                                .percentageOfEntriesAverageValueEqualOrHigherThan4(percentageOfEntriesAverageValueEqualOrHigherThan4)
                                .entriesWithValuesLessThan4(entriesWithValuesLessThan4)
                                .percentageOfEntriesWithValuesLessThan4(percentageOfEntriesWithValuesLessThan4)
                                .unratedEntries(repository.getUnratedEntries())
                                .build();
    }
}
