package com.tsystems.ecare.app.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This class is designed to implement logic of logging for all controllers.
 */
public abstract class AbstractController {

    protected Logger logger;

    public AbstractController(Logger logger) {
        this.logger = logger;
    }

    /**
     * Error handler for backend errors - a 400 status code will be sent back, and the body
     * of the message contains the exception text.
     *
     * @param ex the exception caught
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception ex) {
        logger.error(ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
