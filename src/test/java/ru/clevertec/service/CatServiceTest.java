package ru.clevertec.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.domain.Cat;
import ru.clevertec.entity.CatEntity;
import ru.clevertec.exception.CustomException;
import ru.clevertec.mapper.CatMapper;
import ru.clevertec.repository.CatRepository;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatServiceTest {

    @Mock
    CatRepository repository;
    @Mock
    CatMapper mapper;
    @InjectMocks
    CatService service;

    @Test
    void shouldFindAll() {
        //given
        List<CatEntity> catEntities = TestData.generateCatEntities();
        List<Cat> cats = TestData.generateCats();

        lenient().when(repository.findAll())
                .thenReturn(catEntities); // this won't get called without lenient()

        lenient().when(mapper.toDomains(catEntities))
                .thenReturn(cats); // this won't get called without lenient()

        //when
        List<Cat> actualResult = service.findAll(); //size 2

        //then
        Assertions.assertEquals(2, actualResult.size());

    }

    @Test
    void shouldFindCatByUUID() {
        // given
        UUID catId = UUID.randomUUID();
        CatEntity catEntity = new CatEntity();
        Cat expectedResult = new Cat();

        when(repository.findCatByUUID(catId))
                .thenReturn(Optional.of(catEntity));

        when(mapper.toDomain(catEntity))
                .thenReturn(expectedResult);
        // when
        Cat actualResult = service.findCatByUUID(catId).get();

        // then
        Assertions.assertEquals(expectedResult.getId(), actualResult.getId());
    }

    @Test
    void shouldThrowCustomException_whenCatNotFound() {
        // given
        UUID catId = UUID.randomUUID();

        when(repository.findCatByUUID(catId))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(CustomException.class, () -> service.findCatByUUID(catId));
    }

    @Test
    void shouldCreateNewCat() {
        // given
        Cat cat = TestData.generateCat();
        CatEntity catEntity = TestData.generateCatEntity();

        when(mapper.toEntity(cat))
                .thenReturn(catEntity);

        when(repository.create(catEntity))
                .thenReturn(catEntity);

        when(mapper.toDomain(catEntity))
                .thenReturn(cat);

        // when
        var actualResult = service.create(cat);

        // then
        Assertions.assertEquals(cat.getId(), actualResult.getId());
    }

    @Test
    void shouldUpdateCat_whenCatByUUIDIsPresent() {
        // given
        Cat cat = TestData.generateCat();
        CatEntity catEntityFromDB = TestData.generateCatEntity();
        CatEntity modifyCatEntityFromDB = TestData.generateCatEntity();
        Cat modifiedCat = TestData.generateCat();
        UUID uuid = cat.getId();

        when(repository.findCatByUUID(uuid))
                .thenReturn(Optional.of(catEntityFromDB));

        when(mapper.toEntity(cat))
                .thenReturn(catEntityFromDB);

        when(repository.update(catEntityFromDB, uuid))
                .thenReturn(modifyCatEntityFromDB);

        when(mapper.toDomain(modifyCatEntityFromDB))
                .thenReturn(modifiedCat);

        // when
        var actualResult = service.update(cat, uuid);

        // then
        Assertions.assertEquals(cat.getId(), actualResult.getId());
    }

    @Test
    void shouldNotUpdateCat_whenCatNotFoundByUUID() {
        // given
        Cat cat = TestData.generateCat();
        UUID uuid = UUID.randomUUID();

        when(repository.findCatByUUID(uuid))
                .thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(CustomException.class, () -> service.update(cat, uuid));
    }

    @Test
    void shouldDeleteByUUID() {
        // given
        UUID catId = UUID.randomUUID();
        //does not have to be specified; for illustrative purposes only
        doNothing()
                .when(repository).delete(catId);

        // when
        service.delete(catId);

        // then
        verify(repository).delete(catId);
    }
}