package ru.clevertec.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.CatEntity;
import ru.clevertec.repository.db.Database;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class CatRepository {

    public List<CatEntity> findAll() {
        return Database.generateCatEntities();
    }

    public Optional<CatEntity> findCatByUUID(UUID id) {
        return Database.generateCatEntities().stream()
                .filter(catEntity -> catEntity.getId().equals(id))
                .findFirst();

    }

    public CatEntity create(CatEntity catEntity) {
        catEntity.setId(UUID.randomUUID());
        Database.generateCatEntities().add(catEntity);
        return catEntity;
    }

    public CatEntity update(CatEntity catEntity, UUID id) {
        return catEntity.setId(id);
    }

    public void delete(UUID id) {
        Database.generateCatEntities().stream()
                .dropWhile(catEntity -> catEntity.getId().equals(id))
                .collect(Collectors.toList());

    }
}