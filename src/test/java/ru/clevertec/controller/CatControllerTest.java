package ru.clevertec.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.clevertec.common.Breed;
import ru.clevertec.domain.Cat;
import ru.clevertec.service.CatService;
import ru.clevertec.util.TestData;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;


@WebMvcTest
class CatControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CatService service;

    @Test
    @DisplayName("GET /api/v1/cats return HTTP-status 200 OK and list of cats")
    void shouldFindAll() throws Exception {
        // given
        Mockito.when(this.service.findAll())
                .thenReturn(TestData.generateCats());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }


    @DisplayName("GET /api/v1/cat/{uuid} return HTTP-status 200 OK and сat by UUID")
    @ParameterizedTest
    @ValueSource(strings = {
            "2a53509c-4037-4b49-8569-a7691335ed1b",
            "259c22c2-6652-4637-a084-9e243afde77b",
            "81749b20-9fd1-4884-8b0c-64fac6cb958a",
            "da94e66e-3be8-4332-8284-4b856127edb1",
            "d4bcf4eb-8a37-4fa7-9810-cc991e3c7d78",
            "ae95c477-6b37-42e8-a1ce-bfd6eb27b353",
    })
    void shouldFindCatByUUID(String uuid) throws Exception {
        // given
        Cat cat = TestData.generateCat(uuid);

        Mockito.when(this.service.findCatByUUID(UUID.fromString(uuid)))
                .thenReturn(Optional.of(cat));

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cat/{uuid}", uuid))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TestName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value(Breed.MAINE_COON.name()));

        Mockito.verify(service, Mockito.times(1)).findCatByUUID(UUID.fromString(uuid));
    }

    @DisplayName("GET /api/v1/cat/{uuid} return HTTP-status 404 NOT FOUND")
    @ParameterizedTest
    @ValueSource(strings = {
            "2a53509c-4037-4b49-8569-a7691335ed1b",
            "259c22c2-6652-4637-a084-9e243afde77b",
    })
    void shouldNotFindCat_whenUUIDIsNotValid(String uuid) throws Exception {
        // given

        Mockito.when(this.service.findCatByUUID(UUID.fromString(uuid)))
                .thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/cat/{uuid}", uuid))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @DisplayName("POST /api/v1/cat return HTTP-status 201 CREATED")
    @ParameterizedTest
    @ValueSource(strings = {
            "259c22c2-6652-4637-a084-9e243afde77b"
    })
    void shouldAddNewCat_AndReturnValidEntity(String uuid) throws Exception {
        // given
        Cat cat = TestData.generateCat(uuid);

        Mockito.when(this.service.create(cat)).thenReturn(cat);

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/cat")

                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "id": "259c22c2-6652-4637-a084-9e243afde77b",
                                "breed": "MAINE_COON",
                                "name": "TestName"
                                }
                                """
                        )
                        .accept(MediaType.APPLICATION_JSON))


                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(uuid))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("TestName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.breed").value(Breed.MAINE_COON.name()));
    }


    @DisplayName("DELETE /api/v1/cat/{uuid} return HTTP-status 204 No Content")
    @ParameterizedTest
    @ValueSource(strings = {
            "2a53509c-4037-4b49-8569-a7691335ed1b",
            "259c22c2-6652-4637-a084-9e243afde77b",
            "81749b20-9fd1-4884-8b0c-64fac6cb958a",
            "da94e66e-3be8-4332-8284-4b856127edb1",
            "d4bcf4eb-8a37-4fa7-9810-cc991e3c7d78",
            "ae95c477-6b37-42e8-a1ce-bfd6eb27b353",
    })
    void shouldDeleteCat_whenUUIDIsValid(String uuid) throws Exception {
        // given
        Mockito.when(this.service.findCatByUUID(UUID.fromString(uuid)))
                .thenReturn(Optional.of(new Cat()));

        //does not have to be specified; for illustrative purposes only
        doNothing()
                .when(this.service).delete(UUID.randomUUID());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cat/{uuid}", UUID.fromString(uuid)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    @DisplayName("DELETE /api/v1/cat/{uuid} return HTTP-status 404 Not Found")
    void shouldDeleteCat_whenUUIDIsNotValid() throws Exception {
        // given
        Mockito.when(this.service.findCatByUUID(UUID.randomUUID()))
                .thenReturn(Optional.empty());

        // when, then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/cat/{uuid}", UUID.randomUUID()))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

    }


    @Test
    @DisplayName("PUT /api/v1/cat/{uuid} return HTTP-status 200 OK")
    void shouldUpdateCat_whenUUIDIsPresent() {
        // given

        // when

        // then
    }
}