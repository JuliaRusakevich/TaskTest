package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.domain.Cat;
import ru.clevertec.exception.CustomException;
import ru.clevertec.mapper.CatMapper;
import ru.clevertec.repository.CatRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatService {

    private final CatRepository repository;
    private final CatMapper mapper;


    public List<Cat> findAll() {
        return this.repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<Cat>  findCatByUUID(UUID id) {
        var catEntity = this.repository.findCatByUUID(id)
                .orElseThrow(() -> CustomException.notFoundByCatUUIS(id));
        return Optional.of(this.mapper.toDomain(catEntity));

    }

    public Cat create(Cat cat) {
        var catEntity = this.mapper.toEntity(cat);
        var createdCatEntity = this.repository.create(catEntity);
        return this.mapper.toDomain(createdCatEntity);
    }

    public Cat update(Cat cat, UUID id) {
        if (this.repository.findCatByUUID(id).isPresent()) {
            var catEntity = this.mapper.toEntity(cat);
            var updatedCat = this.repository.update(catEntity, id);
            return this.mapper.toDomain(updatedCat);
        } else {
            throw CustomException.notFoundByCatUUIS(id);
        }
    }

    public void delete(UUID id) {
        this.repository.delete(id);
    }


}


