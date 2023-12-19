package org.kaoden.ws.homework.service.entry.argument;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEntryArgument {
    String name;
    String description;
    List<String> links;
}