package ru.clevertec.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.clevertec.common.Breed;

import java.time.OffsetDateTime;
import java.util.UUID;


@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatEntity {


    private UUID id;
    private Breed breed;
    private String name;
    private OffsetDateTime birthday;

}
