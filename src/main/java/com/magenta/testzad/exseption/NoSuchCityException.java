package com.magenta.testzad.exseption;

import lombok.extern.slf4j.Slf4j;

import java.util.NoSuchElementException;


@Slf4j
public class NoSuchCityException extends NoSuchElementException {
    public NoSuchCityException(int id) {
        super("City with id  " + id + " not found");
        log.warn(getMessage());
    }
}
