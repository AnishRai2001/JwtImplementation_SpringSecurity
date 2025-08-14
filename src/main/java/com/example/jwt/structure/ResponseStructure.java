package com.example.jwt.structure;

public class ResponseStructure<T> {
    private String message;
    private Boolean success;
    private T data;   // Just use T here, no <T> again

    public ResponseStructure() {
    }

    public ResponseStructure(String message, Boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
