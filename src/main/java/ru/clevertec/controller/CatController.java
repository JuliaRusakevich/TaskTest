package ru.clevertec.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.clevertec.domain.Cat;
import ru.clevertec.service.CatService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CatController {

    private final CatService service;

    @RequestMapping(value = "/cats", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Cat> findAll() {
        return this.service.findAll();
    }


    @RequestMapping(value = "/cat/{uuid}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Cat findCatByUUID(@PathVariable("uuid") String uuid) {
        return this.service.findCatByUUID(UUID.fromString(uuid))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/cat", consumes = "application/json", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Cat add(@RequestBody Cat cat) {
        return this.service.create(cat);
    }

    @RequestMapping(value = "/cat/{uuid}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("uuid") String uuid) {
        this.service.findCatByUUID(UUID.fromString(uuid)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        this.service.delete(UUID.fromString(uuid));
    }

    @RequestMapping(value = "/cat/{uuid}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public Cat update(@RequestBody Cat cat, @PathVariable String uuid) {
        return this.service.update(cat, UUID.fromString(uuid));
    }
}
