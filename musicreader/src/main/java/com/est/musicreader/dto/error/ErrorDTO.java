package com.est.musicreader.dto.error;

public class ErrorDTO {
    private int code;
    private String message;
    private String error;

    public ErrorDTO() {
    }

    public ErrorDTO(int code, String message, String error) {
        this.code = code;
        this.message = message;
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
