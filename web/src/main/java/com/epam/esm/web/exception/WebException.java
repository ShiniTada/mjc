package com.epam.esm.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * Web exception
 */
public class WebException {
    private String errorMessage;
    private int errorCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime errorTime;

    /**
     * Instantiates a new Web exception.
     */
    public WebException() {
    }

    /**
     * Instantiates a new Web exception.
     *
     * @param errorMessage the error message
     * @param errorCode    the error code
     */
    public WebException(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.errorTime = LocalDateTime.now();
    }

    /**
     * Get error message
     *
     * @return the error message
     */

    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set error message
     *
     * @param errorMessage the error message
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Get error code
     *
     * @return the error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * Set error code
     *
     * @param errorCode the error code
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Get error time
     *
     * @return the error time
     */
    public LocalDateTime getErrorTime() {
        return errorTime;
    }

    /**
     * Set error time
     *
     * @param errorTime the error time
     */
    public void setErrorTime(LocalDateTime errorTime) {
        this.errorTime = errorTime;
    }
}
