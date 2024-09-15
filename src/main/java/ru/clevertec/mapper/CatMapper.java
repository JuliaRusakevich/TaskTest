package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.clevertec.domain.Cat;
import ru.clevertec.entity.CatEntity;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface CatMapper {

    List<Cat> toDomains(List<CatEntity> catEntities);

    Cat toDomain(CatEntity catEntity);

    CatEntity toEntity(Cat cat);
}
