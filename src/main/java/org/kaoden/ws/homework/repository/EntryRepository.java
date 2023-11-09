package org.kaoden.ws.homework.repository;

import org.kaoden.ws.homework.model.Entry;

import java.util.List;

public interface EntryRepository {

    void creat(Entry entry);

    Entry findById(Long id);

    List<Entry> findByName(String name);

    List<Entry> getAll();

    void update(Long id, Entry entry);

    void delete(Long id);

    Long getFreeId();

}
