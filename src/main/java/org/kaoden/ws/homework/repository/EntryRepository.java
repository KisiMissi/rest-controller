package org.kaoden.ws.homework.repository;

import org.kaoden.ws.homework.model.Entry;

import java.util.List;

public interface EntryRepository {

    Entry create(Entry entry);

    Entry findById(Long id);

    List<Entry> findByName(String name);

    List<Entry> getAll();

    Entry update(Long id, Entry entry);

    void delete(Long id);

    Long getFreeId();

}
