package com.trustar.interview.exception;

import java.io.IOException;

public class APTExtractorException extends RuntimeException {

    public APTExtractorException(String message, IOException cause) {
        super(message, cause);
    }

    public APTExtractorException(String message) {
        super(message);
    }
}
