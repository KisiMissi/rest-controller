package org.kaoden.ws.homework.service;

import lombok.RequiredArgsConstructor;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.EntryRepository;
import org.kaoden.ws.homework.service.argument.CreateEntryArgument;
import org.kaoden.ws.homework.service.argument.UpdateEntryArgument;
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
        return repository.findById(id);
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
        return repository.update(id, Entry.builder()
                                          .id(id)
                                          .name(entry.getName())
                                          .description(entry.getDescription())
                                          .link(entry.getLink())
                                          .build());
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }
}
