package org.kaoden.ws.homework.service.entry;

import lombok.RequiredArgsConstructor;
import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.entry.EntryRepository;
import org.kaoden.ws.homework.service.entry.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.entry.argument.UpdateEntryArgument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryServiceImp implements EntryService {

    private final EntryRepository repository;

    @Override
    public Entry create(CreateEntryArgument entry) {
        return repository.create(Entry.builder()
                                      .id(repository.getFreeId())
                                      .name(entry.getName())
                                      .description(entry.getDescription())
                                      .link(entry.getLink())
                                      .build());
    }

    @Override
    public Entry getExisting(Long id) {
        if (exists(id)) {
            return repository.findById(id);
        } else {
            throw new NotFoundException("There is no entry with this ID: " + id);
        }
    }

    @Override
    public List<Entry> getAll(String searchText) {
        if (searchText == null)
            return repository.getAll();
        else
            return repository.findByName(searchText);
    }

    @Override
    public Entry update(Long id, UpdateEntryArgument entry) {
        if (exists(id)) {
            return repository.update(id, Entry.builder()
                                              .id(id)
                                              .name(entry.getName())
                                              .description(entry.getDescription())
                                              .link(entry.getLink())
                                              .build());

        } else {
            throw new NotFoundException("Impossible update entry with this ID: " + id);
        }
    }

    @Override
    public void delete(Long id) {
        if (exists(id)) {
            repository.delete(id);
        } else {
            throw new NotFoundException("Impossible delete entry with this ID: " + id);
        }
    }

    @Override
    public Boolean exists(Long id) {
        return repository.exists(id);
    }
}
