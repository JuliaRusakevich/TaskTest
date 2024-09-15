package ru.clevertec.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.clevertec.common.Breed;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Cat {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private UUID id;
    private Breed breed;
    private String name;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private OffsetDateTime birthday;
}
