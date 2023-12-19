package org.kaoden.ws.homework.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Data
@Builder
@Entity
@Table(name = "entry")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String description;

    @ElementCollection
    @CollectionTable(name = "entry_links", joinColumns = @JoinColumn(table = "entry_id"))
    @Column(name = "links")
    List<String> links;
}
