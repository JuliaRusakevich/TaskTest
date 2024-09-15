package ru.clevertec.util;

import ru.clevertec.common.Breed;
import ru.clevertec.domain.Cat;
import ru.clevertec.entity.CatEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

public class TestData {

    public static List<CatEntity> generateCatEntities() {
        return List.of(
                new CatEntity()
                        .setId(UUID.randomUUID())
                        .setName("TestBob")
                        .setBreed(Breed.BENGAL_CAT)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2022-02-03"), LocalTime.parse("12:00:00"), ZoneOffset.UTC)),
                new CatEntity()
                        .setId(UUID.randomUUID())
                        .setName("TestMaks")
                        .setBreed(Breed.SPHINX)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.parse("12:00:00"), ZoneOffset.UTC))
        );
    }

    public static List<Cat> generateCats() {
        return List.of(
                new Cat()
                        .setId(UUID.randomUUID())
                        .setName("TestBob")
                        .setBreed(Breed.BENGAL_CAT)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2022-02-03"), LocalTime.parse("12:00:00"), ZoneOffset.UTC)),
                new Cat()
                        .setId(UUID.randomUUID())
                        .setName("TestMaks")
                        .setBreed(Breed.SPHINX)
                        .setBirthday(OffsetDateTime.of(LocalDate.parse("2024-01-01"), LocalTime.parse("12:00:00"), ZoneOffset.UTC))
        );
    }

    public static Cat generateCat() {
        return new Cat()
                .setId(UUID.fromString("2a53509c-4037-4b49-8569-a7691335ed1b"))
                .setName("TestBob")
                .setBreed(Breed.BENGAL_CAT)
                .setBirthday(OffsetDateTime.of(
                        LocalDate.parse("2022-02-03"), LocalTime.parse("12:00:00"), ZoneOffset.UTC));

    }

    public static CatEntity generateCatEntity() {
        return new CatEntity()
                .setId(UUID.fromString("2a53509c-4037-4b49-8569-a7691335ed1b"))
                .setName("TestBob")
                .setBreed(Breed.BENGAL_CAT)
                .setBirthday(OffsetDateTime.of(
                        LocalDate.parse("2022-02-03"), LocalTime.parse("12:00:00"), ZoneOffset.UTC));

    }
}
