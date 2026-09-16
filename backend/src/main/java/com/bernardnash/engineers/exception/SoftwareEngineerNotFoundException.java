package com.bernardnash.engineers.exception;

public class SoftwareEngineerNotFoundException extends RuntimeException {

    public SoftwareEngineerNotFoundException(Integer id) {
        super("Software engineer with id %d was not found".formatted(id));
    }
}
