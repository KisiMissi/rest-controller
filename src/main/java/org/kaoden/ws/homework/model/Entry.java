package org.kaoden.ws.homework.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@FieldDefaults(level = PRIVATE)
public class Entry {
    Long id;
    String name;
    String description;
    String link;
}
