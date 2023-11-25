package org.kaoden.ws.homework.repository.entry;

import lombok.experimental.FieldDefaults;
import org.kaoden.ws.homework.model.Entry;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PRIVATE;

@Repository
@FieldDefaults(level = PRIVATE)
public class EntryRepositoryImpl implements EntryRepository {

    static Long entryId = 0L;
    final Map<Long, Entry> entriesStorage = new HashMap<>();

    @Override
    public Entry create(Entry entry) {
        entriesStorage.put(entry.getId(), entry);
        return entry;
    }

    @Override
    public Entry findById(Long id) {
        return entriesStorage.get(id);

    }

    @Override
    public List<Entry> findByName(String name) {
        return entriesStorage.values()
                             .stream()
                             .filter(entry -> entry.getName()
                                                   .toLowerCase()
                                                   .contains(name.toLowerCase()))
                             .collect(Collectors.toList());
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<>(entriesStorage.values());
    }

    @Override
    public Entry update(Long id, Entry entry) {
        entriesStorage.put(id, entry);
        return entry;
    }

    @Override
    public void delete(Long id) {
        entriesStorage.remove(id);
    }

    @Override
    public Long getFreeId() {
        return entryId++;
    }

    @Override
    public Boolean exists(Long id) {
        return entriesStorage.containsKey(id);
    }
}
