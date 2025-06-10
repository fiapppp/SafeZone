package br.com.safezone.exception;

import jakarta.ws.rs.core.Response;

/**
 * Permite encapsular mensagem e status HTTP.
 */
public class ApiException extends RuntimeException {
    private final Response.Status status;

    public ApiException(String message, Response.Status status) {
        super(message);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}