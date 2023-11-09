package org.kaoden.ws.homework.repository;

import org.kaoden.ws.homework.exception.NotFoundException;
import org.kaoden.ws.homework.model.Entry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EntryRepositoryImp implements EntryRepository {

    private final Map<Long, Entry> entriesStorage = new HashMap<>();
    private static Long entryId = 0L;

    @Override
    public void creat(Entry entry) {
        Long id = getFreeId();
        entry.setId(id);
        entriesStorage.put(id, entry);
    }

    @Override
    public Entry findById(Long id) {
        if (entryExist(id))
            return entriesStorage.get(id);
        else
            throw new NotFoundException("There is no entry with this ID: " + id);
    }

    @Override
    public List<Entry> findByName(String name) {
        return entriesStorage.values()
                .stream()
                .filter(entry -> entry.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<>(entriesStorage.values());
    }

    @Override
    public void update(Long id, Entry entry) {
        if (entryExist(id))
            entriesStorage.put(id, entry);
        else
            throw new NotFoundException("Impossible update entry with this ID: " + id);
    }

    @Override
    public void delete(Long id) {
        if (entryExist(id))
            entriesStorage.remove(id);
        else
            throw new NotFoundException("Impossible delete entry with this ID: "  + id);
    }

    @Override
    public Long getFreeId() {
        return entryId++;
    }

    private boolean entryExist(Long id) {
        return entriesStorage.containsKey(id);
    }
}
