package org.kaoden.ws.homework.service;

import lombok.RequiredArgsConstructor;
import org.kaoden.ws.homework.model.Entry;
import org.kaoden.ws.homework.repository.EntryRepository;
import org.kaoden.ws.homework.service.argument.EntryArgument;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntryServiceImp implements EntryService {

    private final EntryRepository repository;

    @Override
    public void creat(EntryArgument entry) {
        Long id = repository.getFreeId();
        repository.creat(Entry.builder()
                              .id(id)
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
    public List<Entry> search(String searchText) {
        return repository.findByName(searchText);
    }

    @Override
    public List<Entry> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(Long id, EntryArgument entry) {
        repository.update(id, Entry.builder()
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
