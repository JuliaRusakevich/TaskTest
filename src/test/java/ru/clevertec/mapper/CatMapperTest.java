package ru.clevertec.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.clevertec.entity.CatEntity;
import ru.clevertec.util.TestData;


class CatMapperTest {

    private final CatMapper mapper = new CatMapperImpl();

    @Test
    void shouldMapCatEntityToCatDomain() {
        // given
        CatEntity catEntity = TestData.generateCatEntity();
        // when
        var actualResult = mapper.toDomain(catEntity);
        var expectedResult = TestData.generateCat();
        // then
        Assertions.assertEquals(expectedResult, actualResult);
    }

}