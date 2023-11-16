package org.kaoden.ws.homework.service.entry.argument;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntryArgument {
    String name;
    String description;
    String link;
}
