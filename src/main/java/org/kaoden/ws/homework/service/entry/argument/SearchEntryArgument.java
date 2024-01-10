package org.kaoden.ws.homework.service.entry.argument;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchEntryArgument {

    String name;
    String description;

}
