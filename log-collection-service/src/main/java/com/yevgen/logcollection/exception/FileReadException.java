package com.yevgen.logcollection.exception;

public class FileReadException extends RuntimeException {

    public FileReadException(String filename, Throwable cause) {
        super(String.format("Unable to read file %s file", filename), cause);
    }

}
