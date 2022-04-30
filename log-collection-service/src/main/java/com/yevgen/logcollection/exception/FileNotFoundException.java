package com.yevgen.logcollection.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(String filename) {
        super(String.format("File %s is not found", filename));
    }

}
