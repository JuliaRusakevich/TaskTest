package ru.clevertec.repository.db;

import ru.clevertec.common.Breed;
import ru.clevertec.entity.CatEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class Database {

    public static List<CatEntity> generateCatEntities() {
        return List.of(
                new CatEntity()
                        .setId(UUID.randomUUID())
                        .setName("Bob")
                        .setBreed(Breed.BENGAL_CAT)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2022-02-03"), LocalTime.parse("12:00:00"), ZoneOffset.UTC)),
                new CatEntity()
                        .setId(UUID.randomUUID())
                        .setName("Maks")
                        .setBreed(Breed.SPHINX)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.parse("12:00:00"), ZoneOffset.UTC)),
                new CatEntity()
                        .setId(UUID.randomUUID())
                        .setName("Mars")
                        .setBreed(Breed.MAINE_COON)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2022-08-23"), LocalTime.parse("12:00:00"), ZoneOffset.UTC)),
                new CatEntity()
                        .setId(UUID.randomUUID())
                        .setName("May")
                        .setBreed(Breed.SPHINX)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2020-12-18"), LocalTime.parse("12:00:00"), ZoneOffset.UTC))
        );
    }
}
