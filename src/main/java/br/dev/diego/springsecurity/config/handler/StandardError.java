package br.dev.diego.springsecurity.config.handler;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;


@JsonPropertyOrder(value = {"timestamp", "status", "error", "message", "path"})
public class StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "GMT-3")
    @JsonProperty(value = "timestamp")
    private final Instant timestamp;
    @JsonProperty(value = "status")
    private final Integer status;
    @JsonProperty(value = "error")
    private final String error;
    @JsonProperty(value = "message")
    private final String message;
    @JsonProperty(value = "path")
    private final String path;

    public StandardError(Integer status, String error, String message, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

}
