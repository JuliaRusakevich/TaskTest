package ru.clevertec.exception;

import java.util.UUID;

public class CustomException extends RuntimeException {

    private CustomException(String msg) {
        super(msg);
    }

    public static CustomException notFoundByCatUUIS(UUID catId) {
        return new CustomException(
                String.format("NOT FOUND BY ID %s", catId));
    }

}
