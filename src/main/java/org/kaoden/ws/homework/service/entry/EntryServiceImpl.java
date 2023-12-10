package org.kaoden.ws.homework.service.entry;

import lombok.RequiredArgsConstructor;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EntryServiceImpl implements EntryService {

    private final EntryRepository repository;

    @Override
    public Entry create(CreateEntryArgument entry) {
        return repository.save(Entry.builder()
                                    .name(entry.getName())
                                    .description(entry.getDescription())
                                    .links((entry.getLinks()))
                                    .build());
    }

    @Override
    public Entry getExisting(Long id) {
        return repository.findById(id)
                         .orElseThrow(() -> new NotFoundException("There is no entry with this ID: " + id));
    }

    @Override
    public Page<Entry> getAll(String name, String description, Pageable pageable) {
        if (name != null) {
            return repository.findEntriesByNameContainingIgnoreCase(name, pageable);
        }
        else if (description != null) {
            return repository.findEntriesByDescriptionContainingIgnoreCase(description, pageable);
        }
        else {
            return repository.findAll(pageable);
        }
    }

    @Override
    @Transactional
    public Entry update(Long id, UpdateEntryArgument entry) {
        Entry updateEntry = repository.findById(id)
                                      .orElseThrow(() -> new NotFoundException("Impossible update entry with this ID: " + id));

        updateEntry.setName(entry.getName());
        updateEntry.setDescription(entry.getDescription());
        updateEntry.setLinks(entry.getLinks());

        return repository.save(updateEntry);
    }

    @Override
    public void delete(Long id) {
        if (! exists(id)) {
            throw new NotFoundException("Impossible delete entry with this ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Boolean exists(Long id) {
        return repository.existsById(id);
    }
}
